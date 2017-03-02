import {Pipe, PipeTransform} from '@angular/core';
/*
 * Convert from seconds to HH:MM:SS
 *
 * Usage:
 *   value | convertToHours
 */
@Pipe({name: 'convertToHours'})
export class TimeConverterPipe implements PipeTransform {

    transform(value: number): string {
        value = Number(value);
        let h = Math.floor(value / 3600);
        let m = Math.floor(value % 3600 / 60);
        let s = Math.floor(value % 3600 % 60);
        return ((h > 0 ? h + ":" + (m < 10 ? "0" : "") : "") + m + ":" + (s < 10 ? "0" : "") + s);
    }
}

/*
 * Convert from minutes to HH:MM:SS
 *
 * Usage:
 *   value | minToHms
 */
@Pipe({name: 'minToHms'})
export class ConvertMinToHhMmSs implements PipeTransform {

    transform(value: number): string {
        value = Number(value*60);
        let h = Math.floor(value / 3600);
        let m = Math.floor(value % 3600 / 60);

        return ((h > 0 ? h + "t " + (m < 10 ? "0" : "") : "") + m + "m");
    }
}