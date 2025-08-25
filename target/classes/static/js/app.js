// 样本数据查看器主应用
class SampleDataViewer {
    constructor() {
        this.apiBaseUrl = '/api';
        this.currentPage = 0;
        this.pageSize = 20;
        this.sortBy = 'id';
        this.sortDir = 'asc';
        this.filters = {};
        this.columns = this.getDefaultColumns();
        this.visibleColumns = this.getDefaultVisibleColumns();
        
        this.init();
    }

    // 初始化应用
    async init() {
        try {
            await this.loadFilterOptions();
            this.setupEventListeners();
            await this.loadData();
            this.setupColumnSettings();
        } catch (error) {
            console.error('初始化失败:', error);
            this.showError('应用初始化失败');
        }
    }

    // 获取默认列配置
    getDefaultColumns() {
        return [
            { key: 'run', label: 'Run', type: 'text' },
            { key: 'projectId', label: 'Project ID', type: 'text' },
            { key: 'bioSample', label: 'BioSample', type: 'text' },
            { key: 'pmid', label: 'PMID', type: 'text' },
            { key: 'sequencingType', label: 'Sequencing Type', type: 'text' },
            { key: 'libraryLayout', label: 'Library Layout', type: 'text' },
            { key: 'platform', label: 'Platform', type: 'text' },
            { key: 'model', label: 'Model', type: 'text' },
            { key: 'phenotype', label: 'Phenotype', type: 'text' },
            { key: 'diseaseStage', label: 'Disease Stage', type: 'text' },
            { key: 'complication', label: 'Complication', type: 'text' },
            { key: 'smoke', label: 'Smoke', type: 'text' },
            { key: 'recentAntibioticsUse', label: 'Recent Antibiotics Use', type: 'text' },
            { key: 'antibioticsUsed', label: 'Antibiotics Used', type: 'text' },
            { key: 'bodySite', label: 'Body Site', type: 'text' },
            { key: 'bodySiteRaw', label: 'Body Site Raw', type: 'text' },
            { key: 'sex', label: 'Sex', type: 'text' },
            { key: 'age', label: 'Age', type: 'text' },
            { key: 'ageGroup', label: 'Age Group', type: 'text' },
            { key: 'bmi', label: 'BMI', type: 'text' },
            { key: 'patientId', label: 'Patient ID', type: 'text' },
            { key: 'timePoint', label: 'Time Point', type: 'text' },
            { key: 'reads', label: 'Reads', type: 'number' },
            { key: 'shannon', label: 'Shannon', type: 'number' },
            { key: 'observed', label: 'Observed', type: 'number' },
            { key: 'chao1', label: 'Chao1', type: 'number' },
            { key: 'country', label: 'Country', type: 'text' },
            { key: 'continent', label: 'Continent', type: 'text' },
            { key: 'location', label: 'Location', type: 'text' },
            { key: 'latitude', label: 'Latitude', type: 'number' },
            { key: 'longitude', label: 'Longitude', type: 'number' },
            { key: 'ageStart', label: 'Age Start', type: 'number' },
            { key: 'ageEnd', label: 'Age End', type: 'number' }
        ];
    }

    // 获取默认可见列
    getDefaultVisibleColumns() {
        return [
            'run', 'projectId', 'bioSample', 'sequencingType', 'platform', 
            'phenotype', 'bodySite', 'sex', 'ageGroup', 'country', 'continent'
        ];
    }

    // 加载过滤选项
    async loadFilterOptions() {
        try {
            const response = await fetch(`${this.apiBaseUrl}/samples/filter-options`);
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            const options = await response.json();
            this.populateFilterOptions(options);
        } catch (error) {
            console.error('加载过滤选项失败:', error);
            this.showError('加载过滤选项失败');
        }
    }

    // 填充过滤选项到下拉框
    populateFilterOptions(options) {
        const filterMap = {
            'bodySite': options.bodySites,
            'phenotype': options.phenotypes,
            'sequencingType': options.sequencingTypes,
            'platform': options.platforms,
            'country': options.countries,
            'continent': options.continents,
            'sex': options.sexes,
            'ageGroup': options.ageGroups
        };

        Object.entries(filterMap).forEach(([filterId, values]) => {
            const select = document.getElementById(filterId);
            if (select && values) {
                // 清空现有选项（保留"全部"选项）
                select.innerHTML = '<option value="">全部</option>';
                
                // 添加新选项
                values.forEach(value => {
                    if (value && value.trim() !== '') {
                        const option = document.createElement('option');
                        option.value = value;
                        option.textContent = value;
                        select.appendChild(option);
                    }
                });
            }
        });
    }

    // 设置事件监听器
    setupEventListeners() {
        // 过滤表单提交
        const filterForm = document.getElementById('filterForm');
        if (filterForm) {
            filterForm.addEventListener('submit', (e) => {
                e.preventDefault();
                this.handleFilterSubmit();
            });
        }

        // 重置按钮
        const resetBtn = document.getElementById('resetBtn');
        if (resetBtn) {
            resetBtn.addEventListener('click', () => {
                this.resetFilters();
            });
        }

        // 分页大小改变
        const pageSize = document.getElementById('pageSize');
        if (pageSize) {
            pageSize.addEventListener('change', (e) => {
                this.pageSize = parseInt(e.target.value);
                this.currentPage = 0;
                this.loadData();
            });
        }

        // 列设置按钮
        const selectAllBtn = document.getElementById('selectAllColumns');
        const deselectAllBtn = document.getElementById('deselectAllColumns');
        
        if (selectAllBtn) {
            selectAllBtn.addEventListener('click', () => {
                this.selectAllColumns(true);
            });
        }
        
        if (deselectAllBtn) {
            deselectAllBtn.addEventListener('click', () => {
                this.selectAllColumns(false);
            });
        }
    }

    // 处理过滤条件提交
    handleFilterSubmit() {
        const formData = new FormData(document.getElementById('filterForm'));
        this.filters = {};
        
        for (let [key, value] of formData.entries()) {
            if (value && value.trim() !== '') {
                this.filters[key] = value.trim();
            }
        }
        
        this.currentPage = 0;
        this.loadData();
    }

    // 重置过滤条件
    resetFilters() {
        document.getElementById('filterForm').reset();
        this.filters = {};
        this.currentPage = 0;
        this.loadData();
    }

    // 加载数据
    async loadData() {
        try {
            this.showLoading(true);

            const queryParams = {
                page: this.currentPage,
                size: this.pageSize,
                sortBy: this.sortBy,
                sortDir: this.sortDir,
                ...this.filters
            };

            const queryString = new URLSearchParams(queryParams).toString();
            const response = await fetch(`${this.apiBaseUrl}/samples?${queryString}`);
            
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            
            const data = await response.json();
            this.renderTable(data);
            this.renderPagination(data);
            this.updateTotalCount(data.totalElements);

        } catch (error) {
            console.error('加载数据失败:', error);
            this.showError('加载数据失败');
        } finally {
            this.showLoading(false);
        }
    }

    // 渲染表格
    renderTable(data) {
        const tableHeader = document.getElementById('tableHeader');
        const tableBody = document.getElementById('tableBody');
        const emptyState = document.getElementById('emptyState');

        if (!data.content || data.content.length === 0) {
            tableHeader.innerHTML = '';
            tableBody.innerHTML = '';
            emptyState.classList.remove('d-none');
            return;
        }

        emptyState.classList.add('d-none');

        // 渲染表头
        tableHeader.innerHTML = this.visibleColumns.map(columnKey => {
            const column = this.columns.find(col => col.key === columnKey);
            if (!column) return '';
            
            const sortClass = this.sortBy === columnKey ? this.sortDir : '';
            return `
                <th class="sortable ${sortClass}" data-sort="${columnKey}">
                    ${column.label}
                </th>
            `;
        }).join('');

        // 添加排序事件监听器
        tableHeader.querySelectorAll('.sortable').forEach(th => {
            th.addEventListener('click', () => {
                const sortKey = th.dataset.sort;
                if (this.sortBy === sortKey) {
                    this.sortDir = this.sortDir === 'asc' ? 'desc' : 'asc';
                } else {
                    this.sortBy = sortKey;
                    this.sortDir = 'asc';
                }
                this.loadData();
            });
        });

        // 渲染表格数据
        tableBody.innerHTML = data.content.map(row => {
            return `
                <tr>
                    ${this.visibleColumns.map(columnKey => {
                        const value = row[columnKey];
                        const displayValue = this.formatCellValue(value, columnKey);
                        return `<td title="${displayValue || ''}">${displayValue || ''}</td>`;
                    }).join('')}
                </tr>
            `;
        }).join('');
    }

    // 格式化单元格值
    formatCellValue(value, columnKey) {
        if (value === null || value === undefined) {
            return '';
        }

        const column = this.columns.find(col => col.key === columnKey);
        if (!column) return String(value);

        switch (column.type) {
            case 'number':
                return typeof value === 'number' ? value.toLocaleString() : String(value);
            default:
                return String(value);
        }
    }

    // 渲染分页
    renderPagination(data) {
        const pagination = document.getElementById('pagination');
        if (!data.totalPages || data.totalPages <= 1) {
            pagination.innerHTML = '';
            return;
        }

        const currentPage = data.page;
        const totalPages = data.totalPages;
        let paginationHtml = '';

        // 上一页按钮
        paginationHtml += `
            <li class="page-item ${data.first ? 'disabled' : ''}">
                <a class="page-link" href="#" data-page="${currentPage - 1}">
                    <i class="fas fa-chevron-left"></i>
                </a>
            </li>
        `;

        // 页码按钮
        const startPage = Math.max(0, currentPage - 2);
        const endPage = Math.min(totalPages - 1, currentPage + 2);

        if (startPage > 0) {
            paginationHtml += `
                <li class="page-item">
                    <a class="page-link" href="#" data-page="0">1</a>
                </li>
            `;
            if (startPage > 1) {
                paginationHtml += `<li class="page-item disabled"><span class="page-link">...</span></li>`;
            }
        }

        for (let i = startPage; i <= endPage; i++) {
            paginationHtml += `
                <li class="page-item ${i === currentPage ? 'active' : ''}">
                    <a class="page-link" href="#" data-page="${i}">${i + 1}</a>
                </li>
            `;
        }

        if (endPage < totalPages - 1) {
            if (endPage < totalPages - 2) {
                paginationHtml += `<li class="page-item disabled"><span class="page-link">...</span></li>`;
            }
            paginationHtml += `
                <li class="page-item">
                    <a class="page-link" href="#" data-page="${totalPages - 1}">${totalPages}</a>
                </li>
            `;
        }

        // 下一页按钮
        paginationHtml += `
            <li class="page-item ${data.last ? 'disabled' : ''}">
                <a class="page-link" href="#" data-page="${currentPage + 1}">
                    <i class="fas fa-chevron-right"></i>
                </a>
            </li>
        `;

        pagination.innerHTML = paginationHtml;

        // 添加分页点击事件
        pagination.querySelectorAll('.page-link[data-page]').forEach(link => {
            link.addEventListener('click', (e) => {
                e.preventDefault();
                const page = parseInt(e.target.dataset.page);
                if (!isNaN(page) && page >= 0 && page < totalPages) {
                    this.currentPage = page;
                    this.loadData();
                }
            });
        });
    }

    // 设置列显示选项
    setupColumnSettings() {
        const columnCheckboxes = document.getElementById('columnCheckboxes');
        if (!columnCheckboxes) return;

        columnCheckboxes.innerHTML = this.columns.map(column => `
            <div class="form-check column-checkbox">
                <input class="form-check-input" type="checkbox" 
                       id="col_${column.key}" value="${column.key}"
                       ${this.visibleColumns.includes(column.key) ? 'checked' : ''}>
                <label class="form-check-label" for="col_${column.key}">
                    ${column.label}
                </label>
            </div>
        `).join('');

        // 监听复选框变化
        columnCheckboxes.addEventListener('change', () => {
            this.updateVisibleColumns();
        });
    }

    // 更新可见列
    updateVisibleColumns() {
        const checkboxes = document.querySelectorAll('#columnCheckboxes input[type="checkbox"]:checked');
        this.visibleColumns = Array.from(checkboxes).map(cb => cb.value);
        this.loadData(); // 重新渲染表格
    }

    // 全选/取消全选列
    selectAllColumns(select) {
        const checkboxes = document.querySelectorAll('#columnCheckboxes input[type="checkbox"]');
        checkboxes.forEach(cb => {
            cb.checked = select;
        });
        this.updateVisibleColumns();
    }

    // 更新总数显示
    updateTotalCount(count) {
        const totalCount = document.getElementById('totalCount');
        if (totalCount) {
            totalCount.textContent = `总计: ${count.toLocaleString()} 条`;
        }
    }

    // 显示/隐藏加载状态
    showLoading(show) {
        const loadingState = document.getElementById('loadingState');
        const dataTable = document.getElementById('dataTable');
        
        if (show) {
            loadingState?.classList.remove('d-none');
            dataTable?.style.setProperty('opacity', '0.5');
        } else {
            loadingState?.classList.add('d-none');
            dataTable?.style.removeProperty('opacity');
        }
    }

    // 显示错误信息
    showError(message) {
        // 这里可以实现更复杂的错误显示逻辑
        alert(message);
    }
}

// 等待DOM加载完成后初始化应用
document.addEventListener('DOMContentLoaded', () => {
    new SampleDataViewer();
});