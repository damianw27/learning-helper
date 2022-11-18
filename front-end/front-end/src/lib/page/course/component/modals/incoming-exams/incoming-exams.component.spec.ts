import { ComponentFixture, TestBed } from '@angular/core/testing';

import { IncomingExamsComponent } from './incoming-exams.component';

describe('IncomingExamsComponent', () => {
  let component: IncomingExamsComponent;
  let fixture: ComponentFixture<IncomingExamsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ IncomingExamsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(IncomingExamsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
