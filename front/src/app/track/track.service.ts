import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {GlobalService} from "../global.service";
import {Router} from "@angular/router";
import {BehaviorSubject} from "rxjs";

@Injectable({
	providedIn: 'root'
})

export class TrackService {

	trackSubject = new BehaviorSubject<any>({
		tracks: [],
	});

	constructor(
		private http: HttpClient,
		private global: GlobalService,
		private router: Router,
	) {
	}

	private get url() {
		return this.global.backendURL + '/tracks'
	}

	private page(id: number) {
		this.router.navigate(['/track'], {queryParams: {id: id}});
	}

	findAll() {
		this.http.get(
			this.url,
		).subscribe({
			next: (res: any) => this.trackSubject.next({
				...this.trackSubject.value,
				tracks: res.data,
			}),
			error: (e: any) => this.global.error(e),
		})
	}

	find(id: number) {
		return this.http.get(
			this.url + `/${id}`,
		)
	}

	save(track: any, img: any) {
		this.http.post(
			this.url,
			JSON.stringify(track),
			{headers: this.global.headersJsonToken}
		).subscribe({
			next: (res: any) => this.updateImg(res.data.id, img),
			error: (e: any) => this.global.error(e),
		})
	}

	update(id: number, track: any, img: any) {
		this.http.put(
			this.url + `/${id}`,
			JSON.stringify(track),
			{headers: this.global.headersJsonToken}
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
			next: () => this.router.navigate(['/tracks']),
			error: (e: any) => this.global.error(e),
		})
	}

}
