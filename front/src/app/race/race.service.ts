import {Injectable} from '@angular/core';
import {BehaviorSubject} from "rxjs";
import {HttpClient, HttpParams} from "@angular/common/http";
import {GlobalService} from "../global.service";
import {Router} from "@angular/router";

@Injectable({
	providedIn: 'root'
})

export class RaceService {

	raceSubject = new BehaviorSubject<any>({
		races: [],
	});

	constructor(
		private http: HttpClient,
		private global: GlobalService,
		private router: Router,
	) {
	}

	private get url() {
		return this.global.backendURL + '/races'
	}

	private page(id: number) {
		this.router.navigate(['/race'], {queryParams: {id: id}});
	}

	findAll() {
		this.http.get(
			this.url,
		).subscribe({
			next: (res: any) => this.raceSubject.next({
				...this.raceSubject.value,
				races: res.data,
			}),
			error: (e: any) => this.global.error(e),
		})
	}

	find(id: number) {
		return this.http.get(
			this.url + `/${id}`,
		)
	}

	save(race: any, trackId: number, img: any) {
		this.http.post(
			this.url,
			JSON.stringify(race),
			{
				headers: this.global.headersJsonToken,
				params: new HttpParams().appendAll({trackId: trackId})
			}
		).subscribe({
			next: (res: any) => this.updateImg(res.data.id, img),
			error: (e: any) => this.global.error(e),
		})
	}

	update(id: number, race: any, trackId: number, img: any) {
		this.http.put(
			this.url + `/${id}`,
			JSON.stringify(race),
			{
				headers: this.global.headersJsonToken,
				params: new HttpParams().appendAll({trackId: trackId})
			}
		).subscribe({
			next: (res: any) => {
				if (img !== null) this.updateImg(res.data.id, img)
				else this.page(res.data.id)
			},
			error: (e: any) => this.global.error(e),
		})
	}

	private updateImg(id: number, files: any) {
		let formData = new FormData();
		for (let i = 0; i < files.length; i++) {
			formData.append(`file`, files[i]);
		}
		this.http.patch(
			this.url + `/${id}/img`,
			formData,
			{headers: this.global.headersMultipartToken}
		).subscribe({
			next: (res: any) => this.page(res.data.id),
			error: (e: any) => this.global.error(e),
		})
	}

	delete(id: number) {
		this.http.delete(
			this.url + `/${id}`,
			{headers: this.global.headersToken}
		).subscribe({
			next: () => this.router.navigate(['/races']),
			error: (e: any) => this.global.error(e),
		})
	}

}
