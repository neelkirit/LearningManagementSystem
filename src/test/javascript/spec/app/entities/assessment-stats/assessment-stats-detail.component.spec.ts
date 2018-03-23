/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { AmoghServerTestModule } from '../../../test.module';
import { AssessmentStatsDetailComponent } from '../../../../../../main/webapp/app/entities/assessment-stats/assessment-stats-detail.component';
import { AssessmentStatsService } from '../../../../../../main/webapp/app/entities/assessment-stats/assessment-stats.service';
import { AssessmentStats } from '../../../../../../main/webapp/app/entities/assessment-stats/assessment-stats.model';

describe('Component Tests', () => {

    describe('AssessmentStats Management Detail Component', () => {
        let comp: AssessmentStatsDetailComponent;
        let fixture: ComponentFixture<AssessmentStatsDetailComponent>;
        let service: AssessmentStatsService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AmoghServerTestModule],
                declarations: [AssessmentStatsDetailComponent],
                providers: [
                    AssessmentStatsService
                ]
            })
            .overrideTemplate(AssessmentStatsDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AssessmentStatsDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AssessmentStatsService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new AssessmentStats(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.assessmentStats).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
