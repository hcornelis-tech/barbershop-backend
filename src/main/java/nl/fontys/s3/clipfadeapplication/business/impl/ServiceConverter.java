package nl.fontys.s3.clipfadeapplication.business.impl;

import nl.fontys.s3.clipfadeapplication.domain.Service;
import nl.fontys.s3.clipfadeapplication.persistence.entity.ServiceEntity;

final class ServiceConverter {
    private ServiceConverter() {}

    public static Service convert(ServiceEntity services) {
        return Service.builder()
                .id(services.getId())
                .name(services.getName())
                .description(services.getDescription())
                .price(services.getPrice())
                .duration(services.getDuration())
                .build();
    }
}
