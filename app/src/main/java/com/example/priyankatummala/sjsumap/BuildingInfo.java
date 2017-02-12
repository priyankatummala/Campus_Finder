package com.example.priyankatummala.sjsumap;

/**
 * Created by priyanka.tummala on 10/22/16.
 */

public class BuildingInfo {
    String name;
    String code;
    String address;
    String imageSrc;
    double lattitude, longitude;

    public BuildingInfo(){

    }


    public BuildingInfo(String name, String code, String address, String src, double lattitude, double longitude){
        this.name=name;
        this.code=code;
        this.address=address;
        this.imageSrc=src;
        this.lattitude=lattitude;
        this.longitude=longitude;

    }

}
