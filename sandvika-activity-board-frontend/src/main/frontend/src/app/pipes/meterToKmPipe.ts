import {Pipe, PipeTransform} from '@angular/core';
/*
 * Convert from meter to kilometer
 *
 * Usage:
 *   value | meterToKm
 */
@Pipe({name: 'meterToKm'})
export class MeterToKilometerPipe implements PipeTransform {

    transform(value: number, fixed: number): string {
        return (value/1000).toFixed(fixed);
    }
}