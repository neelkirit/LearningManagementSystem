import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Form } from './form.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Form>;

@Injectable()
export class FormService {

    private resourceUrl =  SERVER_API_URL + 'api/forms';

    constructor(private http: HttpClient) { }

    create(form: Form): Observable<EntityResponseType> {
        const copy = this.convert(form);
        return this.http.post<Form>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(form: Form): Observable<EntityResponseType> {
        const copy = this.convert(form);
        return this.http.put<Form>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Form>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Form[]>> {
        const options = createRequestOption(req);
        return this.http.get<Form[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Form[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Form = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Form[]>): HttpResponse<Form[]> {
        const jsonResponse: Form[] = res.body;
        const body: Form[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Form.
     */
    private convertItemFromServer(form: Form): Form {
        const copy: Form = Object.assign({}, form);
        return copy;
    }

    /**
     * Convert a Form to a JSON which can be sent to the server.
     */
    private convert(form: Form): Form {
        const copy: Form = Object.assign({}, form);
        return copy;
    }
}
