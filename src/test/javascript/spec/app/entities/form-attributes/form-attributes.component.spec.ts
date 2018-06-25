/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { AmoghServerTestModule } from '../../../test.module';
import { FormAttributesComponent } from '../../../../../../main/webapp/app/entities/form-attributes/form-attributes.component';
import { FormAttributesService } from '../../../../../../main/webapp/app/entities/form-attributes/form-attributes.service';
import { FormAttributes } from '../../../../../../main/webapp/app/entities/form-attributes/form-attributes.model';

describe('Component Tests', () => {

    describe('FormAttributes Management Component', () => {
        let comp: FormAttributesComponent;
        let fixture: ComponentFixture<FormAttributesComponent>;
        let service: FormAttributesService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AmoghServerTestModule],
                declarations: [FormAttributesComponent],
                providers: [
                    FormAttributesService
                ]
            })
            .overrideTemplate(FormAttributesComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(FormAttributesComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FormAttributesService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new FormAttributes(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.formAttributes[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
