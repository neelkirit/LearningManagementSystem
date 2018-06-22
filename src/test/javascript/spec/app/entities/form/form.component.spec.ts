/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { AmoghServerTestModule } from '../../../test.module';
import { FormComponent } from '../../../../../../main/webapp/app/entities/form/form.component';
import { FormService } from '../../../../../../main/webapp/app/entities/form/form.service';
import { Form } from '../../../../../../main/webapp/app/entities/form/form.model';

describe('Component Tests', () => {

    describe('Form Management Component', () => {
        let comp: FormComponent;
        let fixture: ComponentFixture<FormComponent>;
        let service: FormService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AmoghServerTestModule],
                declarations: [FormComponent],
                providers: [
                    FormService
                ]
            })
            .overrideTemplate(FormComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(FormComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FormService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Form(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.forms[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
