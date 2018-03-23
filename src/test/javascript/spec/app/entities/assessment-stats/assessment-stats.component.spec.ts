/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { AmoghServerTestModule } from '../../../test.module';
import { AssessmentStatsComponent } from '../../../../../../main/webapp/app/entities/assessment-stats/assessment-stats.component';
import { AssessmentStatsService } from '../../../../../../main/webapp/app/entities/assessment-stats/assessment-stats.service';
import { AssessmentStats } from '../../../../../../main/webapp/app/entities/assessment-stats/assessment-stats.model';

describe('Component Tests', () => {

    describe('AssessmentStats Management Component', () => {
        let comp: AssessmentStatsComponent;
        let fixture: ComponentFixture<AssessmentStatsComponent>;
        let service: AssessmentStatsService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AmoghServerTestModule],
                declarations: [AssessmentStatsComponent],
                providers: [
                    AssessmentStatsService
                ]
            })
            .overrideTemplate(AssessmentStatsComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AssessmentStatsComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AssessmentStatsService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new AssessmentStats(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.assessmentStats[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
