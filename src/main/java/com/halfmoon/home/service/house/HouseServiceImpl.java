package com.halfmoon.home.service.house;

import com.halfmoon.home.base.HouseSort;
import com.halfmoon.home.base.HouseStatus;
import com.halfmoon.home.entity.House;
import com.halfmoon.home.entity.HouseDetail;
import com.halfmoon.home.entity.HouseTag;
import com.halfmoon.home.repository.HouseDetailRepository;
import com.halfmoon.home.repository.HouseRepository;
import com.halfmoon.home.repository.HouseTagRepository;
import com.halfmoon.home.service.ServiceMultiResult;
import com.halfmoon.home.service.ServiceResult;
import com.halfmoon.home.web.dto.HouseDTO;
import com.halfmoon.home.web.dto.HouseDetailDTO;
import com.halfmoon.home.web.form.DatatableSearch;
import com.halfmoon.home.web.form.RentSearch;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: xuzhangwang
 */
@Service
public class HouseServiceImpl implements IHouseService{
    
    @Autowired
    HouseRepository houseRepository;

    @Autowired
    HouseDetailRepository houseDetailRepository;

    @Autowired
    HouseTagRepository houseTagRepository;

    @Autowired
    ModelMapper modelMapper;

    private String cdnPrefix;
    
    @Override
    public ServiceMultiResult<HouseDTO> adminQuery(DatatableSearch searchBody) {

        List<HouseDTO> houseDTOS = new ArrayList<>();
        Sort sort = new Sort(Sort.Direction.fromString(searchBody.getDirection()), searchBody.getOrderBy());
        int page = searchBody.getStart() / searchBody.getLength();
        Pageable pageable = new PageRequest(page, searchBody.getLength(), sort);


        /* 使用特殊的字段进行查询,动态生成*/
        Specification<House> specification = (root, query, cb) -> {
            // TODO 这里没有实现登入用户的方法， 所以这里直接的写死用户的adminId为2
            Predicate predicate = cb.equal(root.get("adminId"), 2L);

            // 根据adminId获取没有删除的房源恶
            predicate = cb.and(predicate, cb.notEqual(root.get("status"), HouseStatus.DELETED.getValue()));

            if (searchBody.getCity() != null) {
                predicate = cb.and(predicate, cb.equal(root.get("cityEnName"), searchBody.getCity()));
            }

            if (searchBody.getStatus() != null) {
                predicate = cb.and(predicate, cb.equal(root.get("status"), searchBody.getStatus()));
            }

            if (searchBody.getCreateTimeMin() != null) {
                predicate = cb.and(predicate, cb.greaterThanOrEqualTo(root.get("createTime"), searchBody.getCreateTimeMin()));
            }

            if (searchBody.getCreateTimeMax() != null) {
                predicate = cb.and(predicate, cb.lessThanOrEqualTo(root.get("createTime"), searchBody.getCreateTimeMax()));
            }

            if (searchBody.getTitle() != null) {
                predicate = cb.and(predicate, cb.like(root.get("title"), "%"+ searchBody.getTitle() + "%"));
            }
            return predicate;
        };

        Page<House> houses = houseRepository.findAll(specification, pageable);
        houses.forEach(house -> {
             HouseDTO houseDTO = modelMapper.map(house, HouseDTO.class);
             // TODO 实现图片的cdn图片路劲
             houseDTO.setCover(this.cdnPrefix + house.getCover());
             houseDTOS.add(houseDTO);
        });
        return new ServiceMultiResult<>(houses.getTotalElements(), houseDTOS);
    }

    @Transactional
    @Override
    public ServiceResult updateStatus(Long id, int status) {

        House house = houseRepository.findOne(id);
        if (house == null) {
            return ServiceResult.notFound();
        }

        /**
         * 根据查询到房源状态进行改变
         */
        if (house.getStatus() == status) {
            return new ServiceResult<HouseDTO>(false, "状态没有发生变化");
        }

        if (house.getStatus() == HouseStatus.RENTED.getValue()) {
            return new ServiceResult<HouseDTO>(false, "已出租的房源不允许进行操作");
        }
        if (house.getStatus() == HouseStatus.DELETED.getValue()) {
            return new ServiceResult<HouseDTO>(false, "已经删除的房源不允许进行操作");
        }

        // 除了上述之外，进行修改房源状态
        houseRepository.updateStatus(id, status);


        return ServiceResult.success();
    }

    @Override
    public ServiceMultiResult<HouseDTO> query(RentSearch rentSearch) {

//      Sort sort = new Sort(Sort.Direction.DESC, "lastUpdateTime");

        Sort sort = HouseSort.genterateSort(rentSearch.getOrderBy(), rentSearch.getOrderDirection());
        int page = rentSearch.getStart() / rentSearch.getSize();
        Pageable pageable = new PageRequest(page, rentSearch.getSize(), sort);
        Specification<House> specification = (root, query, cb) -> {
            Predicate predicate = cb.equal(root.get("status"), HouseStatus.PASSES.getValue());

            predicate = cb.and(predicate, cb.equal(root.get("cityEnName"), rentSearch.getCityEnName()));
            return predicate;
        };

        Page<House> houses = houseRepository.findAll(specification, pageable);
        for (House house : houses) {
            System.out.println(house.toString());
        }
        List<HouseDTO> houseDTOS = new ArrayList<>();

        // TODO 还有房源的信息信息HouseDetail和Tags和pictures没有填充进去
        List<Long> houseIds = new ArrayList<>();
        Map<Long, HouseDTO> idToHouseMap = new HashMap<>();
        houses.forEach(house -> {
            HouseDTO houseDTO = modelMapper.map(house, HouseDTO.class);
// TODO 要去改变图片(封面)的路劲
            houseDTO.setCover(this.cdnPrefix + house.getCover());
            houseDTOS.add(houseDTO);

            houseIds.add(house.getId());
            idToHouseMap.put(house.getId(), houseDTO);
        });


        wrapperHouseList(houseIds, idToHouseMap);
        return new ServiceMultiResult<>(houses.getTotalElements(), houseDTOS);
    }

    /**
     * 对于前端的前面传参的是houseDTO对象
     * @param houseId
     * @return
     */
    @Override
    public ServiceResult<HouseDTO> rentHouseDetail(Long houseId) {
        House house = houseRepository.findOne(houseId);
        if (house == null) {
            return ServiceResult.notFound();
        }
        HouseDTO houseDTO = modelMapper.map(house, HouseDTO.class);

        HouseDetail detail = houseDetailRepository.findByHouseId(houseId);
        if (detail == null) {
            return ServiceResult.notFound();
        }
        HouseDetailDTO houseDetailDTO = modelMapper.map(detail, HouseDetailDTO.class);

        houseDTO.setHouseDetail(houseDetailDTO);


        return ServiceResult.of(houseDTO);
    }


    /**
     * houseIds.add(house.getId());
     * idToHouseMap.put(house.getId(), houseDTO);
     * 渲染房源的详细信息
     * @param houseIds
     * @param idToHouseMap
     */
    private void wrapperHouseList(List<Long> houseIds, Map<Long, HouseDTO> idToHouseMap) {
        List<HouseDetail> details = houseDetailRepository.findAllByHouseIdIn(houseIds);
        details.forEach(houseDetail -> {
            HouseDTO houseDTO = idToHouseMap.get(houseDetail.getHouseId());
            HouseDetailDTO houseDetailDTO = modelMapper.map(houseDetail, HouseDetailDTO.class);
            houseDTO.setHouseDetail(houseDetailDTO);
        });

        List<HouseTag> tags = houseTagRepository.findAllByHouseIdIn(houseIds);
        tags.forEach(houseTag -> {
            HouseDTO house = idToHouseMap.get(houseTag.getHouseId());
            house.getTags().add(houseTag.getName());
        });
    }



}
