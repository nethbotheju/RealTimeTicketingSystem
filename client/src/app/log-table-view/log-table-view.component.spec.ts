import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LogTableViewComponent } from './log-table-view.component';

describe('LogTableViewComponent', () => {
  let component: LogTableViewComponent;
  let fixture: ComponentFixture<LogTableViewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [LogTableViewComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LogTableViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
