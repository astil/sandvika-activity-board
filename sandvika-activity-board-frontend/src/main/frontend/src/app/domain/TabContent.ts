import {ActivityType} from "./ActivityType";

export class TabContent {
    code: String;
    decode: String;
    altDecode: String;
    pageNumber: number;
    maxPage: number;
    year: number;
    activityType: ActivityType;

    constructor(code: String, decode: String, altDecode: String) {
        this.code = code;
        this.decode = decode;
        this.altDecode = altDecode;
        this.activityType  = new ActivityType("all", "Alle");
        this.year = new Date().getFullYear();
    }
}
