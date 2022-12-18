import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FormContainer } from './form-container.component';

describe('FormContainerComponent', () => {
  let component: FormContainer;
  let fixture: ComponentFixture<FormContainer>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FormContainer ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FormContainer);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
