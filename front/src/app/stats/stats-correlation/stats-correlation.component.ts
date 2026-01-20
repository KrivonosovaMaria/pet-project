import {Component, Input, OnInit} from '@angular/core';
import html2canvas from "html2canvas";
import {jsPDF} from "jspdf";
import {ChartComponent} from "ng-apexcharts";

@Component({
	selector: 'app-stats-correlation',
	imports: [
		ChartComponent
	],
	templateUrl: './stats-correlation.component.html',
	standalone: true,
})

export class StatsCorrelationComponent implements OnInit{

	@Input() races: any[] = [];

	chartOptions: any;

	ngOnInit(): void {
		setTimeout(() => {
			this.draw()
		}, 500)
	}

	generatePDF() {
		let data: any = document.getElementById('pdf');
		html2canvas(data).then(canvas => {
			const content = canvas.toDataURL('image/png');

			let jsPdf;
			if (canvas.width > canvas.height) {
				jsPdf = new jsPDF('p', 'cm', 'a4');
				jsPdf.addImage(content, 'PNG', 0, 0, 21, 0);
			} else {
				jsPdf = new jsPDF('p', 'pt', [canvas.width, canvas.height]);
				jsPdf.addImage(content, 'PNG', 0, 0, canvas.width, canvas.height);
			}

			jsPdf.save('pdf.pdf');
		});
	}

	draw() {
		// Подготовка данных для scatter plot: [x: продажи, y: отзывы]
		const dataPoints: any[] = [];
		this.races.forEach((race, index) => {
			dataPoints.push({
				x: race.busy || 0,
				y: race.comments?.length || 0,
				raceName: race.name || 'Неизвестная гонка'
			});
		});

		this.chartOptions = {
			series: [
				{
					name: "Корреляция",
					data: dataPoints
				}
			],
			chart: {
				height: 300,
				type: "scatter",
				zoom: {
					enabled: true,
					type: 'xy'
				}
			},
			colors: ["#4A90E2"],
			markers: {
				size: 6,
				strokeColors: "#4A90E2",
				strokeWidth: 2,
				fillColors: "#4A90E2"
			},
			dataLabels: {
				enabled: false
			},
			legend: {
				show: false
			},
			grid: {
				show: true,
				borderColor: '#e0e0e0'
			},
			xaxis: {
				title: {
					text: "Продано билетов"
				},
				labels: {
					style: {
						fontSize: "12px"
					}
				}
			},
			yaxis: {
				title: {
					text: "Количество отзывов"
				},
				labels: {
					style: {
						fontSize: "12px"
					}
				}
			},
			tooltip: {
				custom: ({seriesIndex, dataPointIndex, w}: any) => {
					const dataPoint = w.globals.initialSeries[seriesIndex].data[dataPointIndex];
					const raceName = dataPoint.raceName || 'Неизвестная гонка';
					return '<div style="padding: 10px;">' +
						'<div style="font-weight: bold; margin-bottom: 5px;">' + raceName + '</div>' +
						'<div><strong>Продано билетов:</strong> ' + dataPoint.x + '</div>' +
						'<div><strong>Отзывов:</strong> ' + dataPoint.y + '</div>' +
						'</div>';
				}
			}
		};
	}

}

