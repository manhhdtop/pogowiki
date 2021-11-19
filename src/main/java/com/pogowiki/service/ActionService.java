package com.pogowiki.service;

import com.pogowiki.entity.ActionEntity;
import com.pogowiki.model.request.ActionRequest;
import com.pogowiki.model.request.ListActionRequest;
import com.pogowiki.model.response.ActionResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ActionService {
    Page<ActionResponse> getListAction(ListActionRequest request);

    List<ActionResponse> getActiveAction();

    List<ActionResponse> getActionByRole(Long roleId);

    List<ActionEntity> saveAction(List<ActionRequest> actions);
}
