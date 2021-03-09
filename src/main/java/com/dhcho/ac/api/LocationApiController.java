package com.dhcho.ac.api;

import com.dhcho.ac.entity.Location;
import com.dhcho.ac.repository.LocationRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class LocationApiController {

    private final LocationRepository locationRepository;

    @PostMapping("/api/v1/locations")
    public Result addLocationV1(@RequestBody @Valid LocationRequest request) {
        Location location = new Location(request.getName());
        validateDuplicateLocation(location);
        locationRepository.save(location);
        return new Result(new LocationDto(location));
    }

    @GetMapping("/api/v1/locations")
    public Result findLocationsV1() {
        List<Location> findLocations = locationRepository.findAll();
        List<LocationDto> collect = findLocations.stream()
                .map(LocationDto::new)
                .collect(Collectors.toList());

        return new Result(collect);
    }

    @PutMapping("/api/v1/locations/{id}")
    public Result updateLocationV1(@PathVariable("id") Long id,
                                   @RequestBody @Valid LocationRequest request) {
        Location findLocation = locationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 장소 입니다."));
        findLocation.setName(request.getName());
        locationRepository.flush();
        return new Result(new LocationDto(findLocation));
    }

    @DeleteMapping("/api/v1/locations/{id}")
    public Result removeLocationV1(@PathVariable("id") Long id) {
        locationRepository.deleteById(id);
        return new Result("");
    }

    @Data
    static class LocationRequest {
        private String name;
    }

    @Data
    @AllArgsConstructor
    static class LocationDto {
        private Long locationId;
        private String name;
        private String desc;

        public LocationDto(Location location) {
            this.locationId = location.getId();
            this.name = location.getName();
            this.desc = location.getDesc();
        }
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T data;
    }

    private void validateDuplicateLocation(Location location) {
        List<Location> findLocations = locationRepository.findByName(location.getName());

        if (!findLocations.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 명칭 입니다.");
        }
    }
}
