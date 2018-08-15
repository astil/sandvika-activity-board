import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UpdateHistoricHandicapComponent } from './update-historic-handicap.component';

describe('UpdateHistoricHandicapComponent', () => {
  let component: UpdateHistoricHandicapComponent;
  let fixture: ComponentFixture<UpdateHistoricHandicapComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UpdateHistoricHandicapComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UpdateHistoricHandicapComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
