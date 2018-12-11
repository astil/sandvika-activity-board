import { Injectable } from '@angular/core';
import {Sort} from '@angular/material';
import { ModalAthlete } from '../domain/athlete';
import { Activity } from '../domain/activity';

@Injectable()
export class SortService {

  sortAthletes(sort: Sort, athletes: any): ModalAthlete[] {
    if (!athletes) {
      return;
    }

    if (!sort.active || sort.direction === '') {
      return this.sortAthletes({active: 'ranking', direction: 'asc'}, athletes);
    }

    return athletes.sort((a, b) => {
      const isAsc = sort.direction === 'asc';
      switch (sort.active) {
        case 'ranking': return this.compare(a.ranking, b.ranking, isAsc);
        case 'athleteFirstName': return this.compare(a.athleteFirstName, b.athleteFirstName, isAsc);
        case 'points': return this.compare(a.points, b.points, !isAsc);
        case 'lastActivityDate': return this.compare(a.lastActivityDate, b.lastActivityDate, !isAsc);
        case 'numberOfActivities': return this.compare(a.numberOfActivities, b.numberOfActivities, !isAsc);
        case 'kilometers': return this.compare(a.kilometers, b.kilometers, !isAsc);
        case 'minutes': return this.compare(a.minutes, b.minutes, !isAsc);
        case 'handicap': return this.compare(a.handicap, b.handicap, isAsc);
        default:
        return 0;
      }
    });
  }

  sortActivities(sort: Sort, activities: any): Activity[] {
    if (!activities) {
      return;
    }

    if (!sort.active || sort.direction === '') {
      return this.sortActivities({active: 'startDateLocal', direction: 'asc'}, activities);
    }

    return activities.sort((a, b) => {
      const isAsc = sort.direction === 'asc';
      switch (sort.active) {
        case 'startDateLocal': return this.compare(a.startDateLocal, b.startDateLocal, !isAsc);
        case 'name': return this.compare(a.name, b.name, isAsc);
        case 'type': return this.compare(a.type, b.type, isAsc);
        case 'points': return this.compare(a.points, b.points, !isAsc);
        case 'distanceInMeters': return this.compare(a.distanceInMeters, b.distanceInMeters, !isAsc);
        case 'movingTimeInSeconds': return this.compare(a.movingTimeInSeconds, b.movingTimeInSeconds, !isAsc);
        case 'elapsedTimeInSeconds': return this.compare(a.elapsedTimeInSeconds, b.elapsedTimeInSeconds, !isAsc);
        case 'totalElevationGaininMeters': return this.compare(a.totalElevationGaininMeters, b.totalElevationGaininMeters, !isAsc);
        case 'sufferScore': return this.compare(a.sufferScore, b.sufferScore, !isAsc);
        case 'handicap': return this.compare(a.handicap, b.handicap, isAsc);
        default:
        return 0;
      }
    });
  }

  private compare(a, b, isAsc) {
    return (a < b ? -1 : 1) * (isAsc ? 1 : -1);
  }
}

