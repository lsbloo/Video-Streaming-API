package com.apiservice.apiv1.interfaces;

import com.apiservice.apiv1.dtos.HistoryCreate;
import com.apiservice.apiv1.dtos.UnidadeResponseDTO;

import javax.validation.constraints.NotNull;

public interface HistoryCrudInterface {

    boolean createHistory(HistoryCreate dto, String user_authe);

    boolean updateHistory(@NotNull HistoryCreate dto, Integer id_history);

    boolean deleteHistory(Integer id_history);

    UnidadeResponseDTO getHistory(Integer id_history);
}
