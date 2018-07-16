package com.raddarapp.presentation.general.presenter;

import com.karumi.rosie.domain.usecase.UseCaseHandler;
import com.karumi.rosie.domain.usecase.annotation.Success;
import com.karumi.rosie.domain.usecase.callback.OnSuccessCallback;
import com.raddarapp.data.general.TerritoryMainRepository;
import com.raddarapp.domain.model.RaddarLocation;
import com.raddarapp.domain.model.TerritoryArea;
import com.raddarapp.domain.model.TerritoryMain;
import com.raddarapp.domain.model.builder.RaddarLocationBuilder;
import com.raddarapp.domain.usecase.GetTerritoryArea;
import com.raddarapp.domain.usecase.GetTerritoryMain;
import com.raddarapp.presentation.general.presenter.base.BasePresenterRefreshWithLoading;
import com.raddarapp.presentation.general.viewmodel.TerritoryMainViewModel;
import com.raddarapp.presentation.general.viewmodel.TerritoryViewModel;
import com.raddarapp.presentation.general.viewmodel.mapper.TerritoryMainToTerritoryMainViewModelMapper;

import javax.inject.Inject;

public class FootprintsMainTerritoryPresenter extends BasePresenterRefreshWithLoading<FootprintsMainTerritoryPresenter.View> {
private final TerritoryMainToTerritoryMainViewModelMapper mapperTerritoryMain;
 
    private final GetTerritoryMain getTerritoryMain;
    private final GetTerritoryArea getTerritoryArea;
    private String lastTerritoryKey = null;
    public String parentKey=null;
    public TerritoryMain parentTerritoryMain;
    public TerritoryMain territoryMainId;
    public TerritoryMainRepository territoryMainRepository;
    public String  territoryKey;
    public String[] testReport;
    public int  indice = 0;
    public int  count = 0;
    public int i=0;
    public String[] cols;
    public String[]colIds;
    public String[]colParentIds;
@Inject
    public FootprintsMainTerritoryPresenter(UseCaseHandler useCaseHandler, GetTerritoryMain getTerritoryMain,
            GetTerritoryArea getTerritoryArea, TerritoryMainToTerritoryMainViewModelMapper mapperTerritoryMain) {
        super(useCaseHandler);
       
        this.getTerritoryMain = getTerritoryMain;
        this.mapperTerritoryMain = mapperTerritoryMain;
        this.getTerritoryArea = getTerritoryArea;
    }

	public void onShowTerritoryMainTesting(String strLatLong)  {
        //String strLocation="-32.97985,-55.78521";
        System.out.println("INFORME #TEST TERRITORIOS COORDENADAS:\n");
        count = 0;
        cols=strLatLong.split("\t");

        System.out.println("columnas\n"+cols.toString());
        String[] rows=strLatLong.split("\n");
        colIds =new String [rows.length];
        testReport =new String [rows.length];
        for ( i=0;i<rows.length;i++) {
            testReport[i]="";
            String[] col = rows[i].split("\t");
            territoryKey=col[0];
            colIds[i]=col[0];
            //String coords=col[1];En el caso de pasarle solo dos columnas: id coordsIn
            String coords=col[2]; //En el caso de 3 columnas: id parenId coordsIn
                String[] coord = coords.split(",");
                String latitude = coord[0];
                String longitude = coord[1];

            System.out.println("#TEST coords... " +i+" "+col[0] + " " + latitude +", "+longitude+"\n");
                try {
                    getView().showLoadingTerritoryMainDetails();
                } catch (Exception e) {
                }
                final RaddarLocation raddarLocation = new RaddarLocationBuilder()
                        .withLatitude(Double.valueOf(latitude))
                        .withLongitude(Double.valueOf(longitude))
                        .build();



                createUseCaseCall(getTerritoryMain)
                        .args(raddarLocation,i,colIds[i])
                        .useCaseName(GetTerritoryMain.USE_CASE_GET_TERRITORY_MAIN_BY_COORDINATES_TESTING)
                        .onSuccess(new OnSuccessCallback() {
                            @Success
                                         public  void getTerritoryMainByCoordinates(TerritoryMain territoryMain) {
                                           count++;
                                           indice=territoryMain.getIndice();

                                           lastTerritoryKey = territoryMain.getTerritory().getKey();

                                           territoryKey=colIds[indice];


                                             testReport[indice]="#TEST CoordsIn " +(indice+1) + " " + territoryKey +" "+lastTerritoryKey+"....SUCCESS\t";

                                           if(territoryKey.equals(lastTerritoryKey)) {

                                               testReport[indice]=testReport[indice]+"#TEST IdCompare " +(indice+1) + " " + territoryKey +" "+lastTerritoryKey+"....SUCCESS\n";

                                           }
                                           else {

                                               testReport[indice]=testReport[indice]+"#TEST IdCompare " +(indice+1) + " " + territoryKey +" "+lastTerritoryKey+"....FAIL\n";

                                           }

                                           if(count==testReport.length) {

                                               for ( int i=0;i<testReport.length;i++) {
                                                   territoryMain.getTerritory().setTestReport(territoryMain.getTerritory().getTestReport()+testReport[i]);

                                               }
                                               System.out.println("#TEST REPORT 2...\n " +territoryMain.getTerritory().getTestReport());
                                           }
                                           onShowTerritoryMain(territoryMain);

                                       }
                                   }
                        )
                        .onError(error -> {
                            //testReport[indice]=testReport[indice]+"#TEST CoordsIn " +(indice+1) + " " + territoryKey +" "+lastTerritoryKey+"....FAIL\t";
                            System.out.println("#TEST REPORT 2...All FAIL ");
                            showError();
                            return false;
                        })
                        .execute();


            }


    }
	
	public void onShowTerritoryMainByZoneTesting(String strLatLong) {
        System.out.println("INFORME #TEST TERRITORIOS IDS EN BD:\n");
        count = 0;
        cols=strLatLong.split("\t");
        System.out.println("columnas\n"+cols.toString());
        String[] rows=strLatLong.split("\n");
        colIds =new String [rows.length];
        colParentIds =new String [rows.length];
        testReport =new String [rows.length];
        for ( i=0;i<rows.length;i++) {
            testReport[i] = "";
            String[] col = rows[i].split("\t");
            territoryKey = col[0];
            colIds[i] = col[0];
            colParentIds[i] = col[1];



            System.out.println("#TEST ids... " + i + " " + col[0] + " " +col[1] + "\n");
            try {
                getView().showLoadingTerritoryMainDetails();
            } catch (Exception e) {
            }

            createUseCaseCall(getTerritoryMain)
                    .args(colIds[i],i)
                    .useCaseName(GetTerritoryMain.USE_CASE_GET_TERRITORY_MAIN_BY_ZONE_TESTING)
                    .onSuccess(new OnSuccessCallback() {
                        @Success
                        public void getTerritoryMainByZone(TerritoryMain territoryMain) {
                            count++;
                            indice=territoryMain.getIndice();
                            lastTerritoryKey = territoryMain.getTerritory().getKey();
                            
                            parentKey=territoryMain.getTerritory().getParentKey();
                            testReport[indice]="#TEST idInDb " +(indice+1) + " " + lastTerritoryKey +" "+parentKey+"....SUCCESS\t";



                            if (!parentKey.isEmpty()&& parentKey.equals(colParentIds[indice])){
                                testReport[indice]=testReport[indice]+"#TEST parentIdIn " +(indice+1) + " " + parentKey +" "+colParentIds[indice] +"....SUCCESS\n";
                            }else{

                                testReport[indice]=testReport[indice]+"#TEST parentIdIn " +(indice+1) + " " + parentKey +" "+colParentIds[indice] +"....FAIL\n";
                            }


                            if(count==testReport.length) {

                                for ( int i=0;i<testReport.length;i++) {
                                    territoryMain.getTerritory().setTestReport(territoryMain.getTerritory().getTestReport()+testReport[i]);

                                }
                                System.out.println("#TEST REPORT 2...\n " +territoryMain.getTerritory().getTestReport());
                            }

                            onShowTerritoryMain(territoryMain);
                        }
                    })
                    .onError(error -> {
                        showError();
                        return false;
                    })
                    .execute();

        }
    }
	
	
}