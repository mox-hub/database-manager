<template>
    <div>
        <!-- 小页面标题 -->
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item>
                    <i class="el-icon-lx-cascades"></i> 就业登记管理
                </el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <!-- 就业登记表单 -->
        <div class="container">
            <!-- 标题工具栏 -->
            <div class="handle-box">
                <el-select v-model="query.mode" placeholder="查询模式" class="handle-select mr10">
                    <el-option key="1" label="登记ID查询" value="uid"></el-option>
                    <el-option key="2" label="工作类型查询" value="jid"></el-option>
                    <el-option key="3" label="公司名称查询" value="cid"></el-option>
                </el-select>
                <el-cascader v-if="query.mode ==='jid'" :options="jobMenu" clearable :props="{emitPath:false}" v-model="query.options" placeholder="工作名称" :show-all-levels="false" class="handle-select mr10"></el-cascader>
                <el-cascader v-else-if="query.mode ==='cid'" :options="companyMenu" clearable :props="{emitPath:false}" v-model="query.options" placeholder="公司名称" :show-all-levels="false" class="handle-select mr10"></el-cascader>
                <el-input v-else v-model="query.options" placeholder="参数" class="handle-input mr10"></el-input>
                <el-button type="primary" icon="el-icon-search" @click="handleSearch">搜索</el-button>
            </div>
            <!-- 就业登记表单 -->
            <el-table :data="registrationData" border class="table" ref="multipleTable" header-cell-class-name="table-header">
                <el-table-column prop="registrationId" label="登记表ID"  align="center"></el-table-column>
                <el-table-column prop="companyId" label="公司ID" align="center"></el-table-column>
                <el-table-column prop="jobId" label="工作ID" align="center"></el-table-column>
                <el-table-column prop="requiredNum" label="需求数量" align="center"></el-table-column>
                <el-table-column prop="hiresNum" label="已招聘数量" align="center"></el-table-column>
                <!-- 操作栏 -->
                <el-table-column label="操作" width="180" align="center">
                    <template #default="scope">
                        <el-button type="text" icon="el-icon-edit" @click="handleAdd(scope.$index, scope.row)">登记就业
                        </el-button>
                    </template>
                </el-table-column>
            </el-table>
            <!-- 翻页器 -->
            <div class="pagination">
                <el-pagination background layout="total, prev, pager, next" :current-page="query.pageIndex"
                    :page-size="query.pageSize" :total="pageTotal" @current-change="handlePageChange"></el-pagination>
            </div>
        </div>

        <!-- 添加弹出框 有校验 -->
        <el-dialog title="添加需求登记" v-model="addVisible" width="30%">
            <el-form :model="ruleForm" ref="ruleFormRef" :rules="addRules" label-width="100px">
                <el-alert v-if="ruleForm.able" type="success" show-icon :closable="false" style="margin-bottom:20px">
                    <p>您仍然可以登记</p>
                </el-alert>
                <el-alert v-else type="error" show-icon :closable="false" style="margin-bottom:20px">
                    <p>您不可以登记</p>
                </el-alert>
                <el-form-item label="需求表ID：" prop="registrationId">
                    <el-input v-model="ruleForm.registrationId" disabled placeholder=""></el-input>
                </el-form-item>
                <el-form-item label="公司名称：" prop="companyId">
                    <el-cascader :options="companyMenu" disabled :props="{emitPath:false}" clearable v-model="ruleForm.companyId" placeholder="系别" style="width:350px" class="handle-select mr10"></el-cascader>
                </el-form-item>
                <el-form-item label="工作类型：" prop="JobId">
                    <el-cascader :options="jobMenu" disabled :props="{emitPath:false}" clearable v-model="ruleForm.jobId" placeholder="系别" style="width:350px" class="handle-select mr10"></el-cascader>
                </el-form-item>
                <el-form-item label="需求数量：" prop="requiredNum">
                    <el-input-number disabled v-model="ruleForm.requiredNum" :min="1" :max="100" @change="handleChange" />
                </el-form-item>
                <el-form-item label="已登记数量" prop="hiresNum">
                    <el-input v-model="ruleForm.hiresNum" disabled placeholder=""></el-input>
                </el-form-item>
                <el-form-item v-if="ruleForm.able" label="您的学号" prop="graduateId">
                    <el-select v-model="ruleForm.graduateId" filterable placeholder="">
                        <el-option
                        v-for="item in graduateMenu"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value"
                        />
                    </el-select>
                </el-form-item>
            </el-form>
            <template #footer>
                <span class="dialog-footer">
                    <el-button @click="addVisible = false">取 消</el-button>
                    <el-button type="primary" @click="saveCreate">确 定</el-button>
                </span>
            </template>
        </el-dialog>
    </div>
</template>

<script>

import { ref, reactive } from "vue";
import { ElMessage, ElMessageBox } from "element-plus";
import { getDataNoParam, getDataParam, insertData} from "../../api/index";

export default {
    name: "registrationTable",
    setup() {
        // 可视化 相关数据
        const addVisible = ref(false);
        // data 相关数据
        const registrationData = ref([]);
        const pageTotal = ref(0);
        const menu = ref([]);
        const companyMenu = ref([]);
        const jobMenu = ref([])
        const graduateMenu = ref([])

        // request 相关数据
        const path = "/reg/queryRegistration";
        const query = reactive({
            mode:"uid",
            options:"all",          
            pageIndex:1,
            pageSize:10,
        });

        const createParam = reactive({
            graduateId: "",
            regId: ""
        })
        // 规则校验表单
        const ruleForm = reactive({
            able : false,
            registrationId:"",
            jobId: "",
            companyId: "",
            requiredNum: 0,
            hiresNum: 0,
            graduateId: "",
        });
        
        // 表单规则
        const ruleFormRef = ref()
        // 自定义验证规则

        const addRules = reactive({
            graduateId: [
                { required: true, message: '请输入需求登记ID', trigger: 'blur' },
                { len: 10, message: '学号长度应为10位！', trigger: 'blur' },
            ],
        })

        /** 定义方法 */ 

        // 获取表格数据
        const getFormData = () => {
            getDataParam(query,path).then((res) => {
                console.log(res)
                registrationData.value = res.data
                pageTotal.value = res.pageTotal || 10
            });
        };
        // 获取级联表单的数据
        const getCascadeData = () => {
            getDataNoParam("/dept/getCascadeData").then((res) => {
                console.log(res.data)
                menu.value = res.data
            });
            getDataNoParam("/company/getCascadeData").then((res) => {
                console.log(res.data)
                companyMenu.value = res.data
            });
            getDataNoParam("/job/getCascadeData").then((res) => {
                console.log(res.data)
                jobMenu.value = res.data
            });
            getDataNoParam("/usr/getCascadeData").then((res) => {
                console.log(res.data)
                graduateMenu.value = res.data
            });
        }

        const addEmployeeData = (data) => {
            insertData(data,"/emp/createEmployee").then((res) => {
                console.log(res)
            });
        }

        /** 按钮事件  */

        // 分页导航
        const handlePageChange = (val) => {
            query.pageIndex = val;
            getFormData();
        };

        const handleChange = (value) => {
            console.log(value)
        }       
        // 添加操作 
        let idx = -1;
        const handleAdd = (index, row) => {
            idx = index;
            Object.keys(ruleForm).forEach((item) => {
                ruleForm[item] = row[item];
            });
            ruleForm.hiresNum = parseInt(ruleForm.hiresNum);
            ruleForm.requiredNum = parseInt(ruleForm.requiredNum);
            if (ruleForm.requiredNum >  ruleForm.hiresNum) {
                ruleForm.able = true;
            } 
            else{
                ruleForm.able = false;
            }
            console.log(ruleForm);
            addVisible.value = true;
        }

        // 查询操作
        const handleSearch = () => {
            query.pageIndex = 1;
            getFormData();
        };

        // 保存新增内容 
        const saveCreate = () => {
            addVisible.value = false;
            createParam.graduateId = ruleForm.graduateId;
            createParam.regId = ruleForm.registrationId;
            
            addEmployeeData(createParam);
            ElMessage.success(`登记就业成功`);
            getFormData();
        }

        // setup时执行的函数
        getCascadeData();
        getFormData();

        return {
            query,
            registrationData,
            pageTotal,
            addVisible,
            ruleForm,
            ruleFormRef,
            addRules,
            menu,
            companyMenu,
            jobMenu,
            graduateMenu,
            handlePageChange,
            handleChange,
            handleSearch,
            handleAdd,
            saveCreate,
        };
    },
};
</script>

<style scoped>
.handle-box {
    margin-bottom: 20px;
}

.handle-select {
    width: 200px;
}

.handle-input {
    width: 300px;
    display: inline-block;
}
.table {
    width: 100%;
    font-size: 14px;
}
.red {
    color: #ff0000;
}
.mr10 {
    margin-right: 10px;
}
.table-td-thumb {
    display: block;
    margin: auto;
    width: 40px;
    height: 40px;
}
</style>
