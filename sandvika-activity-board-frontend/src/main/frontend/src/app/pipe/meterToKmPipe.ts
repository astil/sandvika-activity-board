import {Pipe, PipeTransform} from '@angular/core';
/*
 * Convert from meter to kilometer
 *
 * Usage:
 *   value | meterToKm
 */
@Pipe({name: 'meterToKm'})
export class MeterToKilometerPipe implements PipeTransform {

    transform(value: number): string {
        return (value/1000).toFixed(1);
    }
}