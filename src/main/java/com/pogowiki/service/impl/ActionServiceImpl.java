package com.pogowiki.service.impl;

import com.pogowiki.entity.ActionEntity;
import com.pogowiki.exception.BadRequestException;
import com.pogowiki.model.request.ActionRequest;
import com.pogowiki.model.request.ListActionRequest;
import com.pogowiki.model.response.ActionResponse;
import com.pogowiki.repository.ActionRepository;
import com.pogowiki.service.ActionService;
import com.pogowiki.utils.AssertUtil;
import com.pogowiki.utils.MapperUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ActionServiceImpl implements ActionService {
    @Autowired
    private ActionRepository actionRepository;
    @Autowired
    private MapperUtils mapperUtils;

    @Override
    public Page<ActionResponse> getListAction(ListActionRequest request) {
        Pageable pageable = request.getPageable();
        Page<ActionEntity> page;
        if (StringUtils.isBlank(request.getKeyword())) {
            page = actionRepository.findAll(pageable);
        } else {
            String keyword = request.getKeyword().trim();
            page = actionRepository.findByKeyword(keyword, pageable);
        }
        return mapperUtils.mapPage(page);
    }

    @Override
    public List<ActionResponse> getActiveAction() {
        List<ActionEntity> entities = actionRepository.findByStatusOrderByCreatedDateDesc(1);
        return mapperUtils.mapList(entities);
    }

    @Override
    public List<ActionResponse> getActionByRole(Long roleId) {
        List<ActionEntity> entities = actionRepository.findByRole(roleId);
        return mapperUtils.mapList(entities);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public List<ActionEntity> saveAction(List<ActionRequest> actions) {
        List<Long> ids = actions.stream()
                .map(ActionRequest::getId)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        List<ActionEntity> entities = actionRepository.findAllById(ids);
        if (CollectionUtils.isEmpty(entities)) {
            entities = new ArrayList<>();
        }
        List<ActionRequest> newActions = actions.stream()
                .filter(e -> e.getId() == null)
                .peek(action -> action.setStatus(1))
                .collect(Collectors.toList());
        if (!newActions.isEmpty()) {
            List<String> codes = newActions.stream().map(ActionRequest::getCode).collect(Collectors.toList());
            List<ActionEntity> entitiesByCode = actionRepository.findAllByCodeIn(codes);
            AssertUtil.isEmpty(entitiesByCode, "action.codes.exist");
            List<ActionEntity> newEntities = mapperUtils.mapList(newActions);
            try {
                newEntities = actionRepository.saveAll(newEntities);
            } catch (Exception e) {
                throw BadRequestException.build("message.error");
            }
            entities.addAll(newEntities);
        }
        return entities;
    }
}
