package com.pogowiki.service.impl;

import com.pogowiki.entity.ActionEntity;
import com.pogowiki.entity.RoleEntity;
import com.pogowiki.exception.BadRequestException;
import com.pogowiki.model.request.AddRoleRequest;
import com.pogowiki.model.request.RoleRequest;
import com.pogowiki.model.request.UpdateRoleRequest;
import com.pogowiki.model.response.RoleResponse;
import com.pogowiki.repository.RoleRepository;
import com.pogowiki.service.ActionService;
import com.pogowiki.service.RoleService;
import com.pogowiki.utils.AssertUtil;
import com.pogowiki.utils.MapperUtils;
import com.pogowiki.utils.MessageUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Slf4j
public class RoleServiceImpl implements RoleService {
    @Autowired
    private ActionService actionService;
    @Autowired
    private MapperUtils mapperUtils;
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Page<RoleResponse> getListRole(RoleRequest request) {
        Pageable pageable = request.getPageable();
        Page<RoleEntity> page;
        if (StringUtils.isBlank(request.getKeyword())) {
            page = roleRepository.findAll(pageable);
        } else {
            String keyword = request.getKeyword().trim();
            page = roleRepository.findByKeyword(keyword, pageable);
        }
        return mapperUtils.mapPage(page);
    }

    @Override
    public List<RoleResponse> getActiveRole() {
        List<RoleEntity> entities = roleRepository.findByStatusOrderByCreatedDateDesc(1);
        return mapperUtils.mapList(entities);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public RoleResponse addRole(AddRoleRequest request) {
        request.validate();
        RoleEntity roleEntity = roleRepository.findByCode(request.getCode());
        AssertUtil.isNull(roleEntity, MessageUtils.getMessage("role.code.exist", request.getCode()));

        List<ActionEntity> actions = actionService.saveAction(request.getListAction());
        roleEntity = mapperUtils.map(request, RoleEntity.class);
        roleEntity.setActions(actions);
        roleEntity.setStatus(1);
        roleRepository.save(roleEntity);
        return mapperUtils.map(roleEntity, RoleResponse.class);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public RoleResponse update(Long id, UpdateRoleRequest request) {
        request.validate();
        RoleEntity roleEntity = roleRepository.findById(id).orElseThrow(() -> BadRequestException.build(MessageUtils.getMessage("role.not_exist", id)));
        mapperUtils.map(request, RoleEntity.class);
        List<ActionEntity> actionEntities = actionService.saveAction(request.getListAction());
        roleEntity.setActions(actionEntities);
        roleEntity = roleRepository.save(roleEntity);
        return mapperUtils.map(roleEntity, RoleResponse.class);
    }


}
