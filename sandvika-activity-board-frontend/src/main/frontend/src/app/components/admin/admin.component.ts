import { Component, OnInit } from '@angular/core';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {AuthCodeService} from '../../service/auth-code.service';
import {AppRestService} from '../../service/app.rest.service';
import {debounceTime} from 'rxjs/operators';
import {Subject} from 'rxjs/Subject';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html'
})
export class AdminComponent implements OnInit {

  private success = new Subject<string>();
  private error = new Subject<string>();
  successMessage: string;
  errorMessage: string;

  constructor(private modalService: NgbModal,
              private auth: AuthCodeService,
              private appRest: AppRestService) {

  }

  ngOnInit() {
    this.success.subscribe((message) => this.successMessage = message);
    this.success.pipe(
      debounceTime(5000)
    ).subscribe(() => this.successMessage = null);
    this.error.subscribe((message) => this.errorMessage = message);
    this.error.pipe(
      debounceTime(10000)
    ).subscribe(() => this.errorMessage = null);
  }

  open(content) {
    this.modalService.open(content);
  }

  onSuccess(message: string) {
    this.success.next(message);
  }
  onError(message: string) {
    this.error.next(message);
  }
}
