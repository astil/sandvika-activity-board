import { Component, OnInit } from '@angular/core';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {AuthCodeService} from "../../service/auth-code.service";
import {AppRestService} from "../../service/app.rest.service";
import {debounceTime} from "rxjs/operators";
import {Subject} from "rxjs";

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html'
})
export class AdminComponent implements OnInit {

  private success = new Subject<string>();
  successMessage: string;

  constructor(private modalService: NgbModal,
              private auth: AuthCodeService,
              private appRest: AppRestService) {

  }

  ngOnInit() {


    this.success.subscribe((message) => this.successMessage = message);
    this.success.pipe(
      debounceTime(5000)
    ).subscribe(() => this.successMessage = null);
  }

  open(content) {
    this.modalService.open(content);
  }

  onNotify(message: string){
    this.success.next(message);
  }
}
