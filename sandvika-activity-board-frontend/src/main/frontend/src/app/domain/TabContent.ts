export class TabContent {
    code: String;
    decode: String;
    altDecode: String;
    pageNumber: number;
    maxPage: number;
    year: number;
    activityType: String;

    constructor(code: String, decode: String, altDecode: String) {
        this.code = code;
        this.decode = decode;
        this.altDecode = altDecode;
        this.activityType  = "all";
        this.year = new Date().getFullYear();
    }
}
