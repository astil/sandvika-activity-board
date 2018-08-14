import {ActivityType} from './ActivityType';

export class TabContent {
    code: string;
    decode: string;
    altDecode: string;
    pageNumber: number;
    maxPage: number;
    year: number;
    activityType: ActivityType;

    constructor(code: string, decode: string, altDecode: string) {
        this.code = code;
        this.decode = decode;
        this.altDecode = altDecode;
        this.activityType  = new ActivityType('all', 'Alle');
        this.year = new Date().getFullYear();
    }
}
