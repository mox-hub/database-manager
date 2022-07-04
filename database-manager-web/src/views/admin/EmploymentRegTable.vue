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
                    <el-option key="2" label="工作ID查询" value="jid"></el-option>
                    <el-option key="3" label="公司ID查询" value="cid"></el-option>
                </el-select>
                <el-cascader v-if="query.mode ==='prof'" :options="menu" clearable :props="{emitPath:false}" v-model="query.options" placeholder="系别" :show-all-levels="false" class="handle-select mr10"></el-cascader>
                <el-input v-else v-model="query.options" placeholder="参数" class="handle-input mr10"></el-input>
                <el-button type="primary" icon="el-icon-search" @click="handleSearch">搜索</el-button>
                <el-button type="success" icon="el-icon-plus" @click="handleAdd">添加用户</el-button>
            </div>
            <!-- 就业登记表单 -->
            <el-table :data="registrationData" border class="table" ref="multipleTable" header-cell-class-name="table-header">
                <el-table-column prop="registrationId" label="登记表ID"  align="center"></el-table-column>
                <el-table-column prop="jobId" label="工作ID" align="center"></el-table-column>
                <el-table-column prop="companyId" label="公司ID" align="center"></el-table-column>
                <el-table-column prop="requiredNum" label="需求数量" align="center"></el-table-column>
                <el-table-column prop="hiresNum" label="已招聘数量" align="center"></el-table-column>
                <!-- 操作栏 -->
                <el-table-column label="操作" width="180" align="center">
                    <template #default="scope">
                        <el-button type="text" icon="el-icon-edit" @click="handleEdit(scope.$index, scope.row)">编辑
                        </el-button>
                        <el-button type="text" icon="el-icon-delete" class="red"
                            @click="handleDelete(scope.$index, scope.row)">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <!-- 翻页器 -->
            <div class="pagination">
                <el-pagination background layout="total, prev, pager, next" :current-page="query.pageIndex"
                    :page-size="query.pageSize" :total="pageTotal" @current-change="handlePageChange"></el-pagination>
            </div>
        </div>

        <!-- 编辑弹出框 无校验 -->
        <el-dialog title="编辑" v-model="editVisible" width="30%">
            <el-form :model="form" label-width="100px">
            <!-- <el-form rules="rules" label-width="70px"> -->
                <el-form-item label="需求表ID：" prop="registrationId">
                    <el-input v-model="form.registrationId" placeholder=""></el-input>
                </el-form-item>
                <el-form-item label="公司名称：" prop="companyId">
                    <el-cascader :options="companyMenu" :props="{emitPath:false}" clearable v-model="form.companyId" placeholder="系别" style="width:350px" class="handle-select mr10"></el-cascader>
                </el-form-item>
                <el-form-item label="工作类型：" prop="JobId">
                    <el-cascader :options="jobMenu" :props="{emitPath:false}" clearable v-model="form.jobId" placeholder="系别" style="width:350px" class="handle-select mr10"></el-cascader>
                </el-form-item>
                <el-form-item label="需求数量：" prop="requiredNum">
                    <el-input-number v-model="form.requiredNum" :min="1" :max="100" @change="handleChange" />
                </el-form-item>
                <el-form-item label="已登记数量" prop="hiresNum">
                    <el-input v-model="form.hiresNum" disabled placeholder=""></el-input>
                </el-form-item>
            </el-form>
            <template #footer>
                <span class="dialog-footer">
                    <el-button @click="editVisible = false">取 消</el-button>
                    <el-button type="primary" @click="saveEdit">确 定</el-button>
                </span>
            </template>
        </el-dialog>

        <!-- 添加弹出框 有校验 -->
        <el-dialog title="添加需求登记" v-model="addVisible" width="30%">
            <el-form :model="ruleForm" ref="ruleFormRef" :rules="addRules" label-width="100px">
            <!-- <el-form rules="rules" label-width="70px"> -->
                <el-form-item label="需求表ID：" prop="registrationId">
                    <el-input v-model="ruleForm.registrationId" placeholder=""></el-input>
                </el-form-item>
                <el-form-item label="公司名称：" prop="companyId">
                    <el-cascader :options="companyMenu" :props="{emitPath:false}" clearable v-model="ruleForm.companyId" placeholder="系别" style="width:350px" class="handle-select mr10"></el-cascader>
                </el-form-item>
                <el-form-item label="工作类型：" prop="JobId">
                    <el-cascader :options="jobMenu" :props="{emitPath:false}" clearable v-model="ruleForm.jobId" placeholder="系别" style="width:350px" class="handle-select mr10"></el-cascader>
                </el-form-item>
                <el-form-item label="需求数量：" prop="requiredNum">
                    <el-input-number v-model="ruleForm.requiredNum" :min="1" :max="100" @change="handleChange" />
                </el-form-item>
                <el-form-item label="已登记数量" prop="hiresNum">
                    <el-input v-model="ruleForm.hiresNum" disabled placeholder=""></el-input>
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
import { getDataNoParam, getDataParam, insertData, deleteData, updateData } from "../../api/index";

export default {
    name: "registrationTable",
    setup() {
        // 可视化 相关数据
        const addVisible = ref(false);
        const editVisible = ref(false);
        // data 相关数据
        const registrationData = ref([]);
        const pageTotal = ref(0);
        const menu = ref([]);
        const companyMenu = ref([]);
        const jobMenu = ref([])
        // request 相关数据
        const path = "/reg/queryRegistration";
        const query = reactive({
            mode:"uid",
            options:"all",          
            pageIndex:1,
            pageSize:10,

        });
        // 表单
        const form = reactive({
            registrationId:"",
            jobId: "",
            companyId: "",
            requiredNum: 0,
            hiresNum: 0,
        });
        // 规则校验表单
        const ruleForm = reactive({
            registrationId:"",
            jobId: "",
            companyId: "",
            requiredNum: 0,
            hiresNum: 0,
        });
        
        const deleteParam = reactive({
            registrationId:"",
        })
        // 表单规则
        const ruleFormRef = ref()
        // 自定义验证规则
        const validatePass = (rule, value, callback) => {
            //  密码只能由大小写英文字母或数字开头，且由大小写英文字母_.组成
            const reg = /^[A-Za-z0-9][A-Za-z0-9_.]{5,14}$/
            console.log('reg', value.match(reg))
            if (!value.match(reg)) {
                callback(new Error('密码由字母或数字开头，且只能为字母,数字,下划线及（.）'))
            } else {
                callback()
            }
        }

        const addRules = reactive({
            registrationId: [
                { required: true, message: '请输入需求登记ID', trigger: 'blur' },
                { len: 8, message: 'ID长度应为8位！', trigger: 'blur' },
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
        }
        // 添加毕业生数据
        const addRegistrationData = (data) => {
            insertData(data,"/reg/createRegistration").then((res) => {
                console.log(res.data);
            });
        }
        // 更新毕业生数据
        const updateRegistrationData = (data) => {
            updateData(data,"/reg/updateRegistration").then((res) => {
                console.log(res.data)
                // refresh;
            });
        }
        // 删除毕业生数据
        const deleteRegistrationData = (data) => {
            deleteData(data,"/reg/deleteRegistration").then((res) => {
                console.log(res.data)
                // refresh;
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
        const handleAdd = () => {
            addVisible.value = true;
        }
        // 删除操作
        const handleDelete = (index, row) => {
            // 二次确认删除
            ElMessageBox.confirm("确定要删除吗？", "提示", {
                type: "warning",
            })
            .then(() => {

                deleteParam.registrationId = row.registrationId
                deleteRegistrationData(deleteParam);
                ElMessage.success("删除成功");
                getFormData();
                // registrationData.value.splice(index, 1);
            })
            .catch(() => {});
        };
        // 编辑操作
        let idx = -1;
        const handleEdit = (index, row) => {
            idx = index;
            Object.keys(form).forEach((item) => {
                form[item] = row[item];
                console.log(form[item]);
            });
            editVisible.value = true;
        };
        // 查询操作
        const handleSearch = () => {
            query.pageIndex = 1;
            getFormData();
        };

        // 保存编辑内容
        const saveEdit = () => {
            editVisible.value = false;
            Object.keys(form).forEach((item) => {
                registrationData.value[idx][item] = form[item];
            });
            console.log(registrationData.value[idx])
            updateRegistrationData(registrationData.value[idx]);
            ElMessage.success(`修改第 ${idx + 1} 行成功`);
            getFormData();
        };
        // 保存新增内容 
        const saveCreate = () => {
            addVisible.value = false;
            console.log(ruleForm)
            addRegistrationData(ruleForm);
            ElMessage.success(`添加新用户成功`);
            getFormData();
        }

        // setup时执行的函数
        getCascadeData();
        getFormData();

        return {
            query,
            registrationData,
            pageTotal,
            editVisible,
            addVisible,
            form,
            ruleForm,
            ruleFormRef,
            deleteParam,
            addRules,
            menu,
            companyMenu,
            jobMenu,
            handlePageChange,
            handleChange,
            handleDelete,
            handleSearch,
            handleEdit,
            handleAdd,
            saveEdit,
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
