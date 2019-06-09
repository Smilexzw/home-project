package com.halfmoon.home.web.controller.house;

import com.halfmoon.home.base.ApiResponse;
import com.halfmoon.home.base.RentValueBlock;
import com.halfmoon.home.service.ServiceResult;
import com.halfmoon.home.service.house.IAddressService;
import com.halfmoon.home.service.ServiceMultiResult;
import com.halfmoon.home.service.house.IHouseService;
import com.halfmoon.home.service.search.ISearchSerivce;
import com.halfmoon.home.web.dto.*;
import com.halfmoon.home.web.form.RentSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: xuzhangwang
 */
@Controller
public class HouseController {

    @Autowired
    IAddressService addressService;

    @Autowired
    IHouseService houseService;

    @Autowired
    ISearchSerivce searchSerivce;

    /**
     * 获取城市列表
     * @return
     */
    @GetMapping("address/support/cities")
    @ResponseBody
    public ApiResponse getSupportCities() {
        ServiceMultiResult<SupportAddressDTO> result = addressService.findAllCities();
        if (result.getResultSize() == 0) {
            return ApiResponse.ofSuccess(ApiResponse.Status.NOT_FOUNT);
        }
        return ApiResponse.ofSuccess(result.getResult());
    }

    /**
     * 获取对应城市支持区域列表
     * @return
     */
    @GetMapping("address/support/regions")
    @ResponseBody
    public ApiResponse getSupportRegions(@RequestParam(name = "city_name") String cityName) {
        ServiceMultiResult<SupportAddressDTO> addressResult = addressService.findAllRegionsByCityName(cityName);
        if (addressResult.getResult() == null || addressResult.getTotal() < 1) {
            return ApiResponse.ofSuccess(ApiResponse.Status.NOT_FOUNT);
        }
        return ApiResponse.ofSuccess(addressResult.getResult());
    }


    /**
     * 获取对应城市的地铁线路
     * @param cityEnName
     * @return
     */
    @ResponseBody
    @GetMapping("address/support/subway/line")
    public ApiResponse getSupportSubwayLine(@RequestParam(name = "city_name") String cityEnName) {
        if (cityEnName == null) {
            return ApiResponse.ofStatus(ApiResponse.Status.BAD_REQUEST);
        }
        List<SubwayDTO> subways = addressService.findAllSubwayByCityEnName(cityEnName);
        if (subways == null) {
            return ApiResponse.ofStatus(ApiResponse.Status.NOT_FOUNT);
        }
        return ApiResponse.ofSuccess(subways);
    }

    /**
     * 根据对应线路获取对应的站点信息
     * @param subwayId
     * @return
     */
    @ResponseBody
    @GetMapping("address/support/subway/station")
    public ApiResponse getSupportSubwayStation(@RequestParam(name = "subway_id") Long subwayId) {
        List<SubwayStationDTO> stationDTOS = addressService.findSubwayStationBySubway(subwayId);
        if (stationDTOS == null) {
            return ApiResponse.ofStatus(ApiResponse.Status.NOT_FOUNT);
        }
        return ApiResponse.ofSuccess(stationDTOS);
    }


    /**
     * 用户界面，根据用户点击城市，查询该城市的所有信息
     * @param rentSearch
     * @param model
     * @param session
     * @param redirectAttributes
     * @return
     */
    @GetMapping("rent/house")
    public String rentHousePage(@ModelAttribute RentSearch rentSearch,
                                Model model, HttpSession session,
                                RedirectAttributes redirectAttributes) {
        if (rentSearch.getCityEnName() == null) {
            // 如果发现session中也没有cityEnName就直接返回
            String cityEnNameInSession = (String) session.getAttribute("cityEnName");
            if (cityEnNameInSession == null) {
                redirectAttributes.addAttribute("msg", "must_chose_city");
                return "redirect:/index";
            } else {
               rentSearch.setCityEnName(cityEnNameInSession);
            }
        }  else {
            session.setAttribute("cityEnName", rentSearch.getCityEnName());
        }

        /**
         *  第一步 先根据用户点击的城市（根据缩写查询城市名）, 如果不存在就直接返回
         */
        ServiceResult<SupportAddressDTO> city = addressService.findCity(rentSearch.getCityEnName());
        if (!city.isSuccess()) {
            redirectAttributes.addAttribute("msg", "must_chose_city");
            return "redirect:/index";
        }
        model.addAttribute("currentCity", city.getResult());

        // 获取对应城市的区域信息
        ServiceMultiResult<SupportAddressDTO> addressResult = addressService.findAllRegionsByCityName(rentSearch.getCityEnName());
        if (addressResult.getResult() == null || addressResult.getTotal() < 1) {
            redirectAttributes.addAttribute("msg", "must_chose_city");
            return "redirect:/index";
        }

        // 查询房屋信息
        ServiceMultiResult<HouseDTO> serviceMultiResult = houseService.query(rentSearch);

        model.addAttribute("total",serviceMultiResult.getTotal());
        model.addAttribute("houses", serviceMultiResult.getResult());

        if (rentSearch.getRegionEnName() == null) {
            rentSearch.setRegionEnName("*");
        }


        model.addAttribute("searchBody", rentSearch);
        model.addAttribute("regions", addressResult.getResult());


        // 获取租金区间, 由于在数据库没有该字段，所以在类中自己模拟该数据

        model.addAttribute("priceBlocks", RentValueBlock.PRICE_BLOCK);
        model.addAttribute("areaBlocks", RentValueBlock.AREA_BLOCK);
        model.addAttribute("currentPriceBlock", RentValueBlock.matchPrice(rentSearch.getPriceBlock()));

        model.addAttribute("currentAreaBlock", RentValueBlock.matchArea(rentSearch.getAreaBlock()));


        // 获取面积区间，和以上描述一样。

        return "rent-list";
    }


    /**
     * 根据已出租的房源信息查找房源
     * @param houseId
     * @return
     */
    @GetMapping("rent/house/show/{id}")
    public String rentHouseDetail(@PathVariable("id") Long houseId, Model model) {

        if (houseId < 0) {
            return "404";
        }

        ServiceResult<HouseDTO> houseDTOServiceResult = houseService.rentHouseDetail(houseId);
        System.out.println(houseDTOServiceResult.toString());
        if (!houseDTOServiceResult.isSuccess()) {
            return "404";
        }

        /**
         *  第一步 先根据用户点击的城市（根据缩写查询城市名）, 如果不存在就直接返回
         */

        System.out.println(houseDTOServiceResult.getResult().getCityEnName());
        ServiceResult<SupportAddressDTO> city = addressService.findCity(houseDTOServiceResult.getResult().getCityEnName());
        if (!city.isSuccess()) {
            return "redirect:/index";
        }
        model.addAttribute("city", city.getResult());
        model.addAttribute("house", houseDTOServiceResult.getResult());

        return "house-detail";
    }


    @ResponseBody
    @GetMapping("/house/index/add/{id}")
    public void searchHouse(@PathVariable(name = "id") Long id) {
        searchSerivce.index(id);
    }

}
