import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ExamsListElementComponent } from './exams-list-element.component';

describe('ExamsListElementComponent', () => {
  let component: ExamsListElementComponent;
  let fixture: ComponentFixture<ExamsListElementComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ExamsListElementComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ExamsListElementComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
