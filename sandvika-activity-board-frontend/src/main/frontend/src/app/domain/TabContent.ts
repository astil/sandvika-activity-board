export class TabContent {
    code: String;
    decode: String;
    altDecode: String;
    pageNumber: number;
    maxPage: number;
    year: number;

    constructor(code: String, decode: String, altDecode: String) {
        this.code = code;
        this.decode = decode;
        this.altDecode = altDecode;

        this.year = new Date().getFullYear();
    }
}
