package com.raddarapp.domain.usecase;
import com.karumi.rosie.domain.usecase.RosieUseCase;
import com.karumi.rosie.domain.usecase.annotation.UseCase;
import com.karumi.rosie.repository.policy.ReadPolicy;
import com.raddarapp.data.general.TerritoryMainRepository;
import com.raddarapp.domain.model.RaddarLocation;
import com.raddarapp.domain.model.TerritoryMain;

import javax.inject.Inject;

public class GetTerritoryMain extends RosieUseCase {
 public static final String USE_CASE_GET_TERRITORY_MAIN_BY_COORDINATES_TESTING = "getTerritoryMainByCoordinatesTesting";
public static final String USE_CASE_GET_TERRITORY_MAIN_BY_ZONE_TESTING = "getTerritoryMainByZoneTesting";
 private final TerritoryMainRepository territoryMainRepository;

    private String lastTerritoryKey = null;
    @Inject
    public GetTerritoryMain(TerritoryMainRepository territoryMainRepository) {
        this.territoryMainRepository = territoryMainRepository;
    }
@UseCase(name = USE_CASE_GET_TERRITORY_MAIN_BY_COORDINATES_TESTING)
    public void getTerritoryMainByCoordinatesTesting(RaddarLocation raddarLocation,int indice, String territoryKey) throws Exception {
        TerritoryMain territoryMain = territoryMainRepository.getTerritoryMainByCoordinates(raddarLocation);


        territoryMain.setIndice(indice);

        notifySuccess(territoryMain);

    }

@UseCase(name = USE_CASE_GET_TERRITORY_MAIN_BY_ZONE_TESTING)
    public void getTerritoryMainByZoneTesting(String zoneKey, int indice) throws Exception {
        TerritoryMain territoryMain = territoryMainRepository.getTerritoryMainByZone(zoneKey);
        territoryMain.setIndice(indice);
        notifySuccess(territoryMain);
    }
}