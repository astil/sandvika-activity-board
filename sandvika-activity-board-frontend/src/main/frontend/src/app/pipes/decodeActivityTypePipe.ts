import { Pipe, PipeTransform } from '@angular/core';

import { ActivityType, ACTIVITY_TYPES } from '../domain/ActivityType';
/*
 * Display decoded activity type
 *
 * Usage:
 *   value | activity type code
 */
@Pipe({ name: 'decodeActivityType' })
export class DecodeActivityTypePipe implements PipeTransform {
  transform(value: string): string {
    let activityType = value;

    for (let i = 0; i < ACTIVITY_TYPES.length; i++) {
      if (ACTIVITY_TYPES[i].code.toLowerCase() === value.toLowerCase()) {
        activityType = ACTIVITY_TYPES[i].decode;
      }
    }

    return activityType;
  }
}
