package com.halfmoon.home.web.controller.admin;

import com.halfmoon.home.base.ApiDataTableResponse;
import com.halfmoon.home.base.ApiResponse;
import com.halfmoon.home.base.HouseOperation;
import com.halfmoon.home.base.HouseStatus;
import com.halfmoon.home.entity.House;
import com.halfmoon.home.service.ServiceMultiResult;
import com.halfmoon.home.service.ServiceResult;
import com.halfmoon.home.service.house.IHouseService;
import com.halfmoon.home.web.dto.HouseDTO;
import com.halfmoon.home.web.form.DatatableSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * @author: xuzhangwang
 */
@Controller
public class AdminController {

    @Autowired
    IHouseService houseService;

    @Value("${upload.photo.path}")
    private String uploadPhotoPath;

    /**
     * 后台管理中心
     * @return
     */
    @GetMapping("/admin/center")
    public String adminCenterPage() {
        return "admin/center";
    }

    /**
     * 后台欢迎页面
     * @return
     */
    @GetMapping("admin/welcome")
    public String welcomePage() {
        return "admin/welcome";
    }

    /**
     * 管理员登入界面
     * @return
     */
    @GetMapping("/admin/login")
    public String adminLoginPage() {
        return "admin/login";
    }

    /**
     * 添加house页面
     * @return
     */
    @GetMapping("/admin/add/house")
    public String addHousePage() {
        return "admin/house-add";
    }


    /**
     * 房源列表页面
     * @return
     */
    @GetMapping("admin/house/list")
    public String houseListPage() {
        return "admin/house-list";
    }


    /**
     * 修改使用分页
     * @param searchBody
     * @return
     */
    @PostMapping("admin/houses")
    @ResponseBody
    public ApiDataTableResponse house(@ModelAttribute DatatableSearch searchBody) {

        ServiceMultiResult<HouseDTO> result = houseService.adminQuery(searchBody);
        ApiDataTableResponse response = new ApiDataTableResponse(ApiResponse.Status.SUCCESS);
        response.setData(result.getResult());
        response.setRecordsFiltered(result.getTotal());
        response.setRecordTotal(result.getTotal());
        return response;
    }


    @PostMapping(value = "/admin/upload/photo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public ApiResponse upLoadPhoto(@RequestParam("file")MultipartFile file) {
        if (file == null) {
            return ApiResponse.ofStatus(ApiResponse.Status.NOT_VALID_PARAM);
        }

        String filename = file.getOriginalFilename();
        // 获取文件的后缀
        String suffixName = filename.substring(filename.lastIndexOf("."));
        // 使用UUID对图片重新命名
        String fileName = UUID.randomUUID().toString() + suffixName;
        File target = new File(fileName);

        try {
            file.transferTo(target);
        } catch (IOException e) {
            e.printStackTrace();
            return ApiResponse.ofStatus(ApiResponse.Status.INTERNAL_SERVER_ERROR);
        }
        return ApiResponse.ofSuccess(null);
    }


    /**
     * 管理员审核接口，
     * operation为1 代表发布
     * operation为2 代表下架
     * operation为3 代表删除
     * @param id 房源id
     * @param operation 操作符号
     * @return
     */
    @ResponseBody
    @PutMapping("admin/house/operate/{id}/{operation}")
    public ApiResponse operateHouse(@PathVariable(name = "id") Long id,
                                    @PathVariable(name = "operation") int operation) {
        if (id <= 0) {
            return ApiResponse.ofStatus(ApiResponse.Status.NOT_VALID_PARAM);
        }
        ServiceResult result;
        if (operation == HouseOperation.PASS) {
            // 发布房源
            result = this.houseService.updateStatus(id, HouseStatus.PASSES.getValue());
            return ApiResponse.ofStatus(ApiResponse.Status.SUCCESS);
        } else if (operation == HouseOperation.Obtained) {
            // 下架房源
            result = this.houseService.updateStatus(id, HouseStatus.NOT_AUDITED.getValue());
        } else if (operation == HouseOperation.DELETE) {
            // 删除房源
            result = this.houseService.updateStatus(id, HouseStatus.DELETED.getValue());
        } else {
            return ApiResponse.ofStatus(ApiResponse.Status.BAD_REQUEST);
        }
        if (result.isSuccess()) {
            return ApiResponse.ofSuccess(null);
        }
        return ApiResponse.ofMessage(HttpStatus.BAD_REQUEST.value(), result.getMessage());
    }
}
