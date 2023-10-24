package ru.nsu.fit.directors.businessservice.service;

public interface RoleService {
    boolean isUserOwner(Long establishmentId);
    boolean isUserWorker(Long establishmentId);
}
