package gps_usage.API.web;

import gps_usage.API.service.GenericService;

public abstract class GenericController<T, ID> {
    protected final GenericService<T, ID> service;

    protected GenericController(GenericService<T, ID> service) {
        this.service = service;
    }
}
