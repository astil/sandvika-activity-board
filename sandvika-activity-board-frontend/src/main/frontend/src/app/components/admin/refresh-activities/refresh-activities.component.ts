import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {NgForm} from "@angular/forms";
import {AppRestService} from "../../../service/app.rest.service";

@Component({
  selector: 'app-refresh-activities',
  templateUrl: './refresh-activities.component.html'
})
export class RefreshActivitiesComponent implements OnInit {


  isLoading = false;
  @Output()
  notify: EventEmitter<string> = new EventEmitter<string>();


  constructor(private restService: AppRestService) { }

  ngOnInit() {

  }

  refreshActivities(data: NgForm): void {
    this.isLoading = true;
    this.restService.refreshActivities(data.value.pages).subscribe(() => {
      this.notify.emit("Oppdatert");
      this.isLoading = false;
    } );
  }

}
