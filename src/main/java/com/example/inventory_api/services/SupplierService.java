package com.example.inventory_api.services;

import com.example.inventory_api.dtos.supplierDTO.SupplierRequest;
import com.example.inventory_api.dtos.supplierDTO.SupplierResponse;
import com.example.inventory_api.exceptions.BusinessException;
import com.example.inventory_api.exceptions.ResourceNotFoundException;
import com.example.inventory_api.mappers.SupplierMapper;
import com.example.inventory_api.repositories.SupplierRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SupplierService {

    private final SupplierRepository supplierRepository;
    private final SupplierMapper mapper;

    @Transactional
    public SupplierResponse save(SupplierRequest request){
        if (supplierRepository.existsByCnpj(request.cnpj())){
            throw new BusinessException("CNPJ is already in use");
        }

        if (supplierRepository.existsByName(request.name())){
            throw new BusinessException("The name is already in use");
        }

        var supplier = mapper.toSupplier(request);
        return mapper.toSupplierResponse(supplierRepository.save(supplier));
    }

    public List<SupplierResponse> getAll(){
        var suppliers = supplierRepository.findAll();
        return mapper.toSupplierResponseList(suppliers);
    }

    public SupplierResponse getById(Long id){
        var supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Supplier not found"));

        return mapper.toSupplierResponse(supplier);
    }

    @Transactional
    public SupplierResponse update(Long id, SupplierRequest request) {
        var supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Supplier not found"));

        supplier.setName(request.name());
        supplier.setCnpj(request.cnpj());
        supplier.setContact(request.contact());
        supplier.setAddress(request.address());

        return mapper.toSupplierResponse(supplierRepository.save(supplier));
    }

    @Transactional
    public void deleteById(Long id){
        if (!supplierRepository.existsById(id)) {
            throw new BusinessException("Supplier not found");
        }
        supplierRepository.deleteById(id);
    }
}
