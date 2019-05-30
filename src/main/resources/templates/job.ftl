<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>QuartzDemo</title>
    <link rel="stylesheet" href="https://unpkg.com/element-ui/lib/theme-chalk/index.css">
    <script src="https://unpkg.com/vue/dist/vue.js"></script>
    <script src="http://cdn.bootcss.com/vue-resource/1.3.4/vue-resource.js"></script>
    <script src="https://unpkg.com/element-ui/lib/index.js"></script>
    <style>
    </style>
</head>
<body>
<div id="test">
    <div id="top">
        <el-button @click="search" size="medium">查询</el-button>
        <el-button @click="handleadd" type="primary" size="medium" align="right">添加</el-button>
    </div>
    <br/>
    <div>
        <el-table
                ref="testTable"
                :data="tableData"
                style="width:100%"
                border
        >
            <el-table-column
                    prop="jobName"
                    label="任务名称"
                    show-overflow-tooltip></el-table-column>
            <el-table-column
                    prop="jobGroup"
                    label="任务所在组"
                    sortable></el-table-column>
            <el-table-column
                    prop="jobClassName"
                    label="任务类名"
                    show-overflow-tooltip></el-table-column>
            <el-table-column
                    prop="triggerName"
                    label="触发器名称"
                    show-overflow-tooltip></el-table-column>
            <el-table-column
                    prop="triggerGroup"
                    label="触发器所在组"
                    sortable></el-table-column>
            <el-table-column
                    prop="cronExpression"
                    label="CRON表达式"
                    sortable></el-table-column>
            <el-table-column
                    prop="triggerState"
                    label="状态"></el-table-column>
            <el-table-column
                    prop="timeZoneId"
                    label="时区"
                    sortable></el-table-column>
            <el-table-column
                    prop="prevFireDateTime"
                    label="上一次"
                    sortable></el-table-column>
            <el-table-column
                    prop="nextFireDateTime"
                    label="下一次"
                    sortable></el-table-column>
            <el-table-column label="操作" width="300">
                <template slot-scope="scope">
                    <el-button
                            size="small"
                            type="warning"
                            @click="handlePause(scope.$index, scope.row)">暂停
                    </el-button>
                    <el-button
                            size="small"
                            type="info"
                            @click="handleResume(scope.$index, scope.row)">恢复
                    </el-button>
                    <el-button
                            size="small"
                            type="danger"
                            @click="handleDelete(scope.$index, scope.row)">删除
                    </el-button>
                    <el-button
                            size="small"
                            type="success"
                            @click="handleUpdate(scope.$index, scope.row)">修改
                    </el-button>
                </template>
            </el-table-column>
        </el-table>
        <div align="right">
            <el-pagination
                    @size-change="handleSizeChange"
                    @current-change="handleCurrentChange"
                    :current-page="currentPage"
                    :page-sizes="[10, 20, 30, 40]"
                    :page-size="pagesize"
                    layout="total, sizes, prev, pager, next, jumper"
                    :total="totalCount"></el-pagination>
        </div>
    </div>
    <el-dialog title="添加任务" :visible.syn="dialogFormVisible">
        <el-form :model="form">
            <el-form-item label="任务名称" label-width="120px" style="width:35%">
                <el-input v-model="form.jobName" auto-complete="off"></el-input>
            </el-form-item>
            <el-form-item label="任务分组" label-width="120px" style="width:35%">
                <el-input v-model="form.jobGroup" auto-complete="off"></el-input>
            </el-form-item>
            <el-form-item label="表达式" label-width="120px" style="width:35%">
                <el-input v-model="form.cronExpression" auto-complete="off"></el-input>
            </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
            <el-button @click="dialogFormVisible = false">取 消</el-button>
            <el-button type="primary" @click="add">确 定</el-button>
        </div>
    </el-dialog>
    <el-dialog title="修改任务" :visible.syn="updateFormVisible">
        <el-form :model="updateform">
            <el-form-item label="表达式" label-width="120px" style="width:35%">
                <el-input v-model="updateform.cronExpression" auto-complete="off"></el-input>
            </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
            <el-button @click="updateFormVisible = false">取 消</el-button>
            <el-button type="primary" @click="update">确 定</el-button>
        </div>
    </el-dialog>
</div>
<footer align="center"><p>&copy; Quartz 任务管理</p></footer>
<script>
    var vue = new Vue({
        el: "#test",
        data: {
            //表格当前页数据
            tableData: [],
            //请求的URL
            url: 'job/list',
            //默认每页数据量
            pagesize: 10,
            //当前页码
            currentPage: 1,
            //查询的页码
            start: 1,
            //默认数据总数
            totalCount: 0,
            //添加对话框默认可见性
            dialogFormVisible: false,
            //修改对话框默认可见性
            updateFormVisible: false,
            //提交的表单
            form: {
                jobName: '',
                jobGroup: '',
                cronExpression: '',
            },

            updateform: {
                jobName: '',
                jobGroup: '',
                cronExpression: '',
            },
        },

        methods: {
            //从服务器读取数据
            loadData: function (pageNum, pageSize) {
                this.$http.get('${request.contextPath}/job/list?' + 'offset=' + (pageNum-1)*pageSize + '&limit=' + pageSize).then(function (res) {
                    console.log(res)
                    if (res.body.code==200) {
                        this.tableData = res.body.data.rows;
                        this.totalCount = res.body.data.total;
                    } else {
                        this.$message.error(res.body.msg);
                    }

                }, function () {
                    console.log('failed');
                });
            },

            //单行删除
            handleDelete: function (index, row) {
                this.$http.post('${request.contextPath}/job/delete', {
                    "jobClassName": row.jobName,
                    "jobGroupName": row.jobGroup
                }, {emulateJSON: true}).then(function (res) {
                    if (res.body.code==200) {
                        this.$message({
                            message: '删除成功',
                            type: 'success'
                        });
                        this.loadData(this.currentPage, this.pagesize);
                    } else {
                        this.$message.error(res.body.msg);
                    }
                }, function () {
                    console.log('failed');
                });
            },

            //暂停任务
            handlePause: function (index, row) {
                this.$http.post('${request.contextPath}/job/pause', {
                    "jobClassName": row.jobName,
                    "jobGroupName": row.jobGroup
                }, {emulateJSON: true}).then(function (res) {
                    if (res.body.code==200) {
                        this.$message({
                            message: '操作成功',
                            type: 'success'
                        });
                        this.loadData(this.currentPage, this.pagesize);
                    } else {
                        this.$message.error(res.body.msg);
                    }
                }, function () {
                    console.log('failed');
                });
            },

            //恢复任务
            handleResume: function (index, row) {
                this.$http.post('${request.contextPath}/job/resume', {
                    "jobClassName": row.jobName,
                    "jobGroupName": row.jobGroup
                }, {emulateJSON: true}).then(function (res) {
                    if (res.body.code==200) {
                        this.$message({
                            message: '操作成功',
                            type: 'success'
                        });
                        this.loadData(this.currentPage, this.pagesize);
                    } else {
                        this.$message.error(res.body.msg);
                    }
                }, function () {
                    console.log('failed');
                });
            },

            //搜索
            search: function () {
                this.loadData(this.currentPage, this.pagesize);
            },

            //弹出对话框
            handleadd: function () {
                this.dialogFormVisible = true;
            },

            //添加
            add: function () {
                this.$http.post('${request.contextPath}/job/add', {
                    "jobClassName": this.form.jobName,
                    "jobGroupName": this.form.jobGroup,
                    "cronExpression": this.form.cronExpression
                }, {emulateJSON: true}).then(function (res) {
                    if (res.body.code==200) {
                        this.$message({
                            message: '添加成功',
                            type: 'success'
                        });
                        this.loadData(this.currentPage, this.pagesize);
                        this.dialogFormVisible = false;
                    } else {
                        this.$message.error(res.body.msg);
                    }
                }, function () {
                    console.log('failed');
                });
            },

            //更新
            handleUpdate: function (index, row) {
                console.log(row)
                this.updateFormVisible = true;
                this.updateform.jobName = row.jobName;
                this.updateform.jobGroup = row.jobGroup;
            },

            //更新任务
            update: function () {
                this.$http.post
                ('${request.contextPath}/job/reschedule',
                    {
                        "jobClassName": this.updateform.jobName,
                        "jobGroupName": this.updateform.jobGroup,
                        "cronExpression": this.updateform.cronExpression
                    }, {emulateJSON: true}
                ).then(function (res) {
                    if (res.body.code==200) {
                        this.$message({
                            message: '更新成功',
                            type: 'success'
                        });
                        this.loadData(this.currentPage, this.pagesize);
                        this.updateFormVisible = false;
                    } else {
                        this.$message.error(res.body.msg);
                    }
                }, function () {
                    console.log('failed');
                });

            },

            //每页显示数据量变更
            handleSizeChange: function (val) {
                this.pagesize = val;
                this.loadData(this.currentPage, this.pagesize);
            },

            //页码变更
            handleCurrentChange: function (val) {
                this.currentPage = val;
                this.loadData(this.currentPage, this.pagesize);
            },

        },


    });

    //载入数据
    vue.loadData(vue.currentPage, vue.pagesize);
</script>
</body>
</html>
