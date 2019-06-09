package com.halfmoon.home.service.search;

import com.google.gson.Gson;
import com.halfmoon.home.entity.House;
import com.halfmoon.home.entity.HouseDetail;
import com.halfmoon.home.entity.HouseTag;
import com.halfmoon.home.repository.HouseDetailRepository;
import com.halfmoon.home.repository.HouseRepository;
import com.halfmoon.home.repository.HouseTagRepository;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.delete.DeleteRequestBuilder;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryAction;
import org.elasticsearch.index.reindex.DeleteByQueryRequestBuilder;
import org.elasticsearch.rest.RestStatus;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 使用slf4j进行日志管理
 * @author: xuzhangwang
 */
@Slf4j
@Service
public class SearchSerivceImpl implements ISearchSerivce{

    @Autowired
    HouseRepository houseRepository;

    @Autowired
    HouseDetailRepository houseDetailRepository;

    @Autowired
    HouseTagRepository houseTagRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    TransportClient client;

    @Autowired
    Gson gson;

    private static final String INDEX = "xunwu";

    private static final String TYPE = "house";

    @Override
    public boolean index(Long houseId) {
        House house = houseRepository.findOne(houseId);
        if (house == null) {
            log.error("Index house {} does not exist!", houseId);
            return false;
        }
        HouseIndexTemplate indexTemplate = modelMapper.map(house, HouseIndexTemplate.class);
        HouseDetail houseDetail = houseDetailRepository.findByHouseId(houseId);
        if (houseDetail == null) {
            log.error("house details does exist");
            return false;
        }
        modelMapper.map(houseDetail, indexTemplate);
        // TODO模拟tags数据
        List<HouseTag> houseTags = houseTagRepository.findAllByHouseIdIn(houseId);
        if (houseTags != null) {
            List<String> tags = new ArrayList<>();
            houseTags.forEach(houseTag -> {
                tags.add(houseTag.getName());
            });
            indexTemplate.setTags(tags);
        }

        // 上面查询逻辑的完成，接下来查询es中是否存在该条记录的信息
        SearchRequestBuilder requestBuilder = this.client.prepareSearch(INDEX).setTypes(TYPE)
                .setQuery(QueryBuilders.termQuery(HouseIndexKey.HOUSE_ID, houseId));
        log.debug(requestBuilder.toString());
        SearchResponse searchResponse = requestBuilder.get();
        // 获取到根据id查询到所有击中的数量
        long totalHits = searchResponse.getHits().getTotalHits();
        boolean success = true;
        if (totalHits == 0) {
            success = create(indexTemplate);
        } else if (totalHits == 1) {
            String esId = searchResponse.getHits().getAt(0).getId();
            success = update(esId, indexTemplate);
        } else {
            success = deleteAndCreate(totalHits, indexTemplate);
        }

        if (!success) {
            log.error("index to house operation not success {}", indexTemplate.getHouseId());
            return false;
        } else {
            return true;
        }

    }

    /**
     * 创建索引
     * @param indexTemplate
     * @return
     */
    private boolean create(HouseIndexTemplate indexTemplate) {
        String jsonObject = gson.toJson(indexTemplate);
        System.out.println(jsonObject.toString());
        IndexResponse response = this.client.prepareIndex(INDEX, TYPE).setSource(jsonObject, XContentType.JSON).get();
        log.debug("create index with house" + indexTemplate.getHouseId());
        // 判断当前创建索引是否成功！
        if (response.status() == RestStatus.CREATED) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 根据es的id进行数据的更新
     * 根据HouseIndexTemplate模板创建的更新索引
     * @param indexTemplate
     */
    private boolean update(String esId, HouseIndexTemplate indexTemplate) {
        UpdateResponse response = this.client.prepareUpdate(INDEX, TYPE, esId).setDoc(gson.toJson(indexTemplate), XContentType.JSON).get();
        if (response.status() == RestStatus.OK) {
            log.debug("update house with house " + indexTemplate.getHouseId());
            return true;
        } else {
            return false;
        }
    }

    /**
     * 查询删除
     * @param indexTemplate
     * @return
     */
    public boolean deleteAndCreate(Long totalHits, HouseIndexTemplate indexTemplate) {
        DeleteByQueryRequestBuilder deleteByQueryRequestBuilder = DeleteByQueryAction.INSTANCE.newRequestBuilder(client)
                .filter(QueryBuilders.termQuery(HouseIndexKey.HOUSE_ID, indexTemplate.getHouseId())).source(INDEX);

        BulkByScrollResponse response = deleteByQueryRequestBuilder.get();

        // 删除的行数
        long deleted = response.getDeleted();
        if (totalHits != deleted) {
            log.warn("need delete {} row, but delete {} row", totalHits, deleted);
            return false;
        } else {
            return create(indexTemplate);
        }
    }

    @Override
    public void remove(Long houseId) {

    }
}
