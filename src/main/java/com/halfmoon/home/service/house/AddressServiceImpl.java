package com.halfmoon.home.service.house;

import com.halfmoon.home.entity.Subway;
import com.halfmoon.home.entity.SubwayStation;
import com.halfmoon.home.entity.SupportAddress;
import com.halfmoon.home.repository.SubwayRepository;
import com.halfmoon.home.repository.SubwayStationRepository;
import com.halfmoon.home.repository.SupportAddressRepository;
import com.halfmoon.home.service.ServiceMultiResult;
import com.halfmoon.home.service.ServiceResult;
import com.halfmoon.home.web.dto.SubwayDTO;
import com.halfmoon.home.web.dto.SubwayStationDTO;
import com.halfmoon.home.web.dto.SupportAddressDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: xuzhangwang
 */
@Service
public class AddressServiceImpl implements IAddressService{

    @Autowired
    private SupportAddressRepository supportAddressRepository;

    @Autowired
    SubwayRepository subwayRepository;

    @Autowired
    SubwayStationRepository subwayStationRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public ServiceMultiResult<SupportAddressDTO> findAllCities() {
        List<SupportAddress> addresses = supportAddressRepository.findAllByLevel(SupportAddress.Level.CITY.getValue());
        List<SupportAddressDTO> addressDTOS = new ArrayList<>();
        for (SupportAddress supportAddress : addresses) {
            SupportAddressDTO target = modelMapper.map(supportAddress, SupportAddressDTO.class);
            addressDTOS.add(target);
        }

        return new ServiceMultiResult<>(addressDTOS.size(), addressDTOS);
    }


    @Override
    public Map<SupportAddress.Level, SupportAddressDTO> findCityAndRegion(String cityEnName, String regionEnName) {
        Map<SupportAddress.Level, SupportAddressDTO> result = new HashMap<>();

        return null;
    }

    @Override
    public ServiceMultiResult<SupportAddressDTO> findAllRegionsByCityName(String cityName) {
        if (cityName == null) {
            return new ServiceMultiResult<>(0, null);
        }
        List<SupportAddressDTO> result = new ArrayList<>();
        List<SupportAddress> regions = supportAddressRepository.findByLevelAndBelongTo(SupportAddress.Level.REGION.getValue(), cityName);
        for (SupportAddress region : regions) {
            result.add(modelMapper.map(region, SupportAddressDTO.class));
        }
        return new ServiceMultiResult<>(result.size(), result);
    }

    @Override
    public List<SubwayDTO> findAllSubwayByCityEnName(String cityEnName) {
        List<SubwayDTO> result = new ArrayList<>();
        List<Subway> subways = subwayRepository.findByCityEnName(cityEnName);
        if (subways.isEmpty()) {
            return result;
        }
        for (Subway subway : subways) {
            result.add(modelMapper.map(subway, SubwayDTO.class));
        }
        return result;
    }

    @Override
    public List<SubwayStationDTO> findSubwayStationBySubway(Long subwayId) {
        List<SubwayStationDTO> results = new ArrayList<>();
        List<SubwayStation> stations = subwayStationRepository.findAllBySubwayId(subwayId);
        if (stations.isEmpty() || stations.size() < 1) {
            return null;
        }
        for (SubwayStation station : stations) {
            results.add(modelMapper.map(station, SubwayStationDTO.class));
        }
        return results;
    }

    @Override
    public ServiceResult<SupportAddressDTO> findCity(String cityEnName) {
        if (cityEnName == null) {
            return ServiceResult.notFound();
        }
        SupportAddress supportAddress = supportAddressRepository.findByEnName(cityEnName);
        if (supportAddress == null) {
            return ServiceResult.notFound();
        }
        SupportAddressDTO supportAddressDTO = modelMapper.map(supportAddress, SupportAddressDTO.class);
        return ServiceResult.of(supportAddressDTO);
    }

}
