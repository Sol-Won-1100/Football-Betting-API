package com.football.betting.api.ui.controller;

import com.football.betting.api.service.impl.BetServiceImpl;
import com.football.betting.api.shared.dto.BetDto;
import com.football.betting.api.ui.model.response.BetRest;
import com.football.betting.api.ui.model.response.GameRest;
import com.football.betting.api.ui.model.response.HomeDrawAwayRest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/bets")
public class BetController {

    @Autowired
    BetServiceImpl betService;

    @GetMapping(path="/historic")
    public List<BetRest> getHistoricBets() {
        List<BetDto> betsDto = betService.getHistoricBets();
        return convertDtoToResponse(betsDto);
    }

    @GetMapping(path="/future")
    public List<BetRest> getFutureBets() {
        List<BetDto> betsDto = betService.getFutureBets();
        return convertDtoToResponse(betsDto);
    }

    private List<BetRest> convertDtoToResponse(List<BetDto> betsDto) {
        ArrayList<BetRest> returnObj = new ArrayList<>();
        for (BetDto bDto: betsDto) {
            BetRest bRest = new BetRest();
            BeanUtils.copyProperties(bDto, bRest);
            GameRest gRest = new GameRest();
            BeanUtils.copyProperties(bDto.getGame(), gRest);
            bRest.setGame(gRest);
            bRest.setOdds(new HomeDrawAwayRest(bDto.getHomeOdds(), bDto.getDrawOdds(), bDto.getAwayOdds()));
            returnObj.add(bRest);
        }
        return returnObj;
    }

}
