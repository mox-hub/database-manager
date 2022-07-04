<template>
    <div>
        <!-- 小页面标题 -->
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item>
                    <i class="el-icon-lx-cascades"></i> 毕业生信息管理
                </el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <!-- 用户表单 -->
        <div class="container">
            <!-- 标题工具栏 -->
            <div class="handle-box">
                <el-select v-model="query.mode" placeholder="查询模式" class="handle-select mr10">
                    <el-option key="1" label="学院名称查询" value="uname"></el-option>
                    <el-option key="2" label="学院ID查询" value="uid"></el-option>
                </el-select>
                <el-input v-model="query.options" placeholder="参数" class="handle-input mr10"></el-input>
                <el-button type="primary" icon="el-icon-search" @click="handleSearch">搜索</el-button>
                <el-button type="success" icon="el-icon-plus" @click="handleAdd">添加学院</el-button>
            </div>
            <!-- 学院表单 -->
            <el-table :data="departmentData" border class="table" ref="multipleTable" header-cell-class-name="table-header">
                <el-table-column prop="departmentId" label="学院ID"  align="center"></el-table-column>
                <el-table-column prop="departmentName" label="学院名称" align="center"></el-table-column>
                <el-table-column prop="departmentInfo" label="学院简介" align="center"></el-table-column>
                <el-table-column prop="departmentTel" label="办公室电话" align="center"></el-table-column>
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
        <el-dialog title="编辑学院" v-model="editVisible" width="30%">
            <el-form label-width="100px">
                <el-form-item label="学院ID：">
                    <el-input v-model="form.departmentId" disabled placeholder=""></el-input>
                </el-form-item>
                <el-form-item label="学院名称：">
                    <el-input v-model="form.departmentName" placeholder=""></el-input>
                </el-form-item>
                <el-form-item label="学院信息：">
                    <el-input v-model="form.departmentInfo" placeholder=""></el-input>
                </el-form-item>
                <el-form-item label="办公室电话：">
                    <el-input v-model="form.departmentTel" placeholder=""></el-input>
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
        <el-dialog title="添加学院" v-model="addVisible" width="30%">
            <el-form :model="ruleForm" ref="ruleFormRef" :rules="addRules" label-width="100px">
            <!-- <el-form rules="rules" label-width="70px"> -->
                <el-form-item label="学院ID：" prop="departmentId">
                    <el-input v-model="ruleForm.departmentId" placeholder=""></el-input>
                </el-form-item>
                <el-form-item label="学院名称：" prop="departmentName">
                    <el-input v-model="ruleForm.departmentName" placeholder=""></el-input>
                </el-form-item>
                <el-form-item label="学院信息：" prop="departmentInfo">
                    <el-input v-model="ruleForm.departmentInfo" placeholder=""></el-input>
                </el-form-item>
                <el-form-item label="办公室电话：" prop="departmentTel">
                    <el-input v-model="ruleForm.departmentTel" placeholder=""></el-input>
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
    name: "DepartmentTable",
    setup() {
        // 可视化 相关数据
        const addVisible = ref(false);
        const editVisible = ref(false);
        // data 相关数据
        const departmentData = ref([]);
        const pageTotal = ref(0);
        const menu = ref([]);
        // request 相关数据
        const path = "/dept/queryDepartment";
        const query = reactive({
            mode:"uid",
            options:"all",          
            pageIndex:1,
            pageSize:10,

        });
        // 表单
        const form = reactive({
            departmentId:"",
            departmentName: "",
            departmentInfo: "",
            departmentTel: "",
        });
        // 规则校验表单
        const ruleForm = reactive({
            departmentId:"",
            departmentName: "",
            departmentInfo: "",
            departmentTel: "",

        });
        const deleteParam = reactive({
             departmentId:"",
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
            departmentId: [
                { required: true, message: '请输入学院ID', trigger: 'blur' },
                { len: 4, message: 'ID长度应为4位！', trigger: 'blur' },
            ],

            departmentName: [
                { required: true, message: '请输入学院名称！', trigger: 'blur' },
                { min: 2, max: 20, message: '名称长度应介于2到20之间', trigger: 'blur' },
                { type: 'string', message: '请输入汉字！', trigger: 'blur' },
            ],
        })

        /** 定义方法 */ 

        // 获取表格数据
        const getFormData = () => {
            getDataParam(query,path).then((res) => {
                console.log(res)
                departmentData.value = res.data
                pageTotal.value = res.pageTotal || 10
            });
        };
        // 获取级联表单的数据
        const getCascadeData = () => {
            getDataNoParam("/dept/getCascadeData").then((res) => {
                console.log(res.data)
                menu.value = res.data
            });
        }
        // 添加毕业生数据
        const addDepartmentData = (data) => {
            insertData(data,"/dept/createDepartment").then((res) => {
                console.log(res.data);
            });
        }
        // 更新毕业生数据
        const updateDepartmentData = (data) => {
            updateData(data,"/dept/updateDepartment").then((res) => {
                console.log(res.data)
                // refresh;
            });
        }
        // 删除毕业生数据
        const deleteDepartmentData = (data) => {
            deleteData(data,"/dept/deleteDepartment").then((res) => {
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

                deleteParam.departmentId = row.departmentId
                deleteDepartmentData(deleteParam);
                ElMessage.success("删除成功");
                getFormData();
                // departmentData.value.splice(index, 1);
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
            console.log(form)
            updateDepartmentData(form);
            ElMessage.success(`修改第 ${idx + 1} 行成功`);
            getFormData();
        };
        // 保存新增内容 
        const saveCreate = () => {
            addVisible.value = false;
            console.log(ruleForm)
            addDepartmentData(ruleForm);
            ElMessage.success(`添加新用户成功`);
            getFormData();
        }

        // setup时执行的函数
        getCascadeData();
        getFormData();

        return {
            query,
            departmentData,
            pageTotal,
            editVisible,
            addVisible,
            form,
            ruleForm,
            ruleFormRef,
            deleteParam,
            addRules,
            menu,
            handlePageChange,
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
