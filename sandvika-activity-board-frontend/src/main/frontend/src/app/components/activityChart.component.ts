import {
  Component,
  Input,
  OnInit,
  ViewChild,
  ChangeDetectorRef
} from '@angular/core';
import { Activity } from '../domain/activity';
import { empty } from 'rxjs/Observer';
import { ActivityType, ACTIVITY_TYPES } from '../domain/ActivityType';
import { ChartsModule, BaseChartDirective } from 'ng2-charts';

@Component({
  selector: 'app-activity-chart',
  templateUrl: 'activityChart.component.html'
})
export class ActivityChartComponent implements OnInit {
  @Input() activities: Activity[];
  @Input() selectedActivityType: string;
  @ViewChild('baseChart') chart: BaseChartDirective;

  disableActivitySelection: boolean;
  activityTypes = ACTIVITY_TYPES;

  navOptions = [
    {
      id: 'velocity',
      title: 'Gjennomsnittshastighet',
      filterOnActivityType: true
    },
    {
      id: 'handicap',
      title: 'Handicap-utvikling',
      filterOnActivityType: false
    }
  ];

  selectedNavOption: {
    id: string;
    title: string;
    filterOnActivityType: boolean;
  };

  // bouvet colors (from styleguide)
  private orange = {
    backgroundColor: '#FFAD78',
    borderColor: '#FC863A',
    pointBackgroundColor: '#FF6400',
    pointBorderColor: '#FFF',
    pointHoverBackgroundColor: '#FFF',
    pointHoverBorderColor: '#FFAD78'
  };

  private yellow = {
    backgroundColor: '#FFDD8E',
    borderColor: '#FFCC55',
    pointBackgroundColor: '#FBBD2D',
    pointBorderColor: '#FFF',
    pointHoverBackgroundColor: '#FFF',
    pointHoverBorderColor: '#FFDD8E'
  };

  public labels = [];
  public datasets = [];
  public options = {};
  public colors = [this.orange, this.yellow];
  public legend = true;
  public type = 'line';

  constructor(private changeDetector: ChangeDetectorRef) {}

  ngOnInit(): void {
    this.disableActivitySelection =
      this.selectedActivityType && this.selectedActivityType !== 'all';

    if (!this.activities) {
      this.activities = [];
    }

    this.select(this.navOptions[0]);
  }

  public select(selected: any): void {
    this.selectedNavOption = selected;
    this.reset();

    switch (selected.id) {
      case 'handicap':
        this.drawHandicap();
        break;
      case 'velocity':
        this.drawVelocity();
        break;
    }
    this.refreshChart();
  }

  private reset() {
    this.labels = [];
    this.datasets = [];
    this.options = {};
  }

  private refreshChart() {
    // Workaround to fix faulty ng2-charts issue with updating labels, colors, options etc.
    if (this.chart) {
      this.changeDetector.detectChanges();
      this.chart.ngOnDestroy();
      this.chart.chart = this.chart.getChartBuilder(this.chart.ctx);
    }
  }

  private drawVelocity(): void {
    const velocityData = [];
    const filteredActivities = this.filterActivitiesOnType();

    for (let i = 0; i < filteredActivities.length; i++) {
      const j = filteredActivities.length - (1 + i);
      const activity = filteredActivities[j];

      const formattedDate = this.convertDate(activity.startDateLocal);
      const velocity = this.calculateVelocity(
        activity.distanceInMeters,
        activity.movingTimeInSeconds
      );

      this.labels[i] = formattedDate;
      velocityData[i] = { t: formattedDate, y: velocity };
    }

    this.setOptions('Tid', 'km / h');
    this.colors = [this.orange];
    this.datasets = [{ data: velocityData, label: 'Gjennomsnittshastighet' }];
  }

  private drawHandicap(): void {
    const handicapData = [];

    for (let i = 0; i < this.activities.length; i++) {
      const j = this.activities.length - (1 + i);

      const formattedDate = this.convertDate(this.activities[j].startDateLocal);
      const handicap = this.activities[j].handicap.toPrecision(2);

      this.labels[i] = formattedDate;
      handicapData[i] = { t: formattedDate, y: handicap };
    }

    this.setOptions('Tid', 'Handicap');
    this.colors = [this.yellow];
    this.datasets = [{ data: handicapData, label: 'Handicaputvikling' }];
  }

  private filterActivitiesOnType(): Activity[] {
    let filteredActivities = [];

    if (this.selectedActivityType && this.selectedActivityType !== 'all') {
      filteredActivities = this.activities.filter(
        data => data.type === this.selectedActivityType
      );
    } else {
      filteredActivities = this.activities;
    }
    return filteredActivities;
  }

  private setOptions(xAxisLabel: string, yAxisLabel: string) {
    this.options = {
      scales: {
        xAxes: [
          {
            scaleLabel: {
              display: true,
              labelString: xAxisLabel
            }
          }
        ],
        yAxes: [
          {
            scaleLabel: {
              display: true,
              labelString: yAxisLabel
            }
          }
        ]
      }
    };
  }

  private convertDate(inputFormat): any {
    function pad(s) {
      return s < 10 ? '0' + s : s;
    }
    const d = new Date(inputFormat);
    return [pad(d.getDate()), pad(d.getMonth() + 1), d.getFullYear()].join('/');
  }

  private calculateVelocity(distanceInMetres, timeInSeconds): number {
    const distanceIsValid =
      distanceInMetres && !isNaN(distanceInMetres) && distanceInMetres !== 0;
    const timeIsValid =
      timeInSeconds && !isNaN(timeInSeconds) && timeInSeconds !== 0;

    let velocity = 0;

    if (distanceIsValid && timeIsValid) {
      velocity = distanceInMetres / timeInSeconds;
    }

    return this.convertmetersPerSecToKmPerHour(velocity);
  }

  private convertmetersPerSecToKmPerHour(metersPerSecond: number): number {
    const kmPerHour = metersPerSecond * 3.6;

    return +kmPerHour.toPrecision(3);
  }
}
