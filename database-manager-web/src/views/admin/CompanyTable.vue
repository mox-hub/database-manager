<template>
    <div>
        <!-- 小页面标题 -->
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item>
                    <i class="el-icon-lx-cascades"></i> 公司信息管理
                </el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <!-- 公司表单 -->
        <div class="container">
            <!-- 标题工具栏 -->
            <div class="handle-box">
                <el-select v-model="query.mode" placeholder="查询模式" class="handle-select mr10">
                    <el-option key="1" label="公司名查询" value="uname"></el-option>
                    <el-option key="2" label="公司ID查询" value="uid"></el-option>
                </el-select>

                <el-input v-model="query.options" placeholder="参数" class="handle-input mr10"></el-input>
                <el-button type="primary" icon="el-icon-search" @click="handleSearch">搜索</el-button>
                <el-button type="success" icon="el-icon-plus" @click="handleAdd">添加公司</el-button>
            </div>
            <!-- 公司表单 -->
            <el-table :data="companyData" border class="table" ref="multipleTable" header-cell-class-name="table-header">
                <el-table-column prop="companyId" label="公司ID"  align="center"></el-table-column>
                <el-table-column prop="companyName" label="公司名" align="center"></el-table-column>
                <el-table-column prop="contactDetails" label="联系方式" align="center"></el-table-column>
                <el-table-column label="地址" align="center">
                    <template #default="scope">
                        <el-tag>{{scope.row.address}}</el-tag>
                    </template>
                </el-table-column>
                 <el-table-column prop="detailedAddress" label="详细地址" align="center"></el-table-column>
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
            <el-form label-width="100px">
                <el-form-item label="公司ID：" prop="companyId">
                    <el-input v-model="form.companyId" placeholder="请输入公司ID"></el-input>
                </el-form-item>
                <el-form-item label="公司名：" prop="companyName">
                    <el-input v-model="form.companyName" placeholder="请输入公司名"></el-input>
                </el-form-item>
                <el-form-item label="联系方式：" prop="contactDetails">
                    <el-input v-model="form.contactDetails" placeholder="请输入联系方式（邮箱或电话）"></el-input>
                </el-form-item>
                <el-form-item label="地址：" prop="company">
                    <el-cascader :options="areaData" clearable v-model="form.address" placeholder="地址" style="width:350px" class="handle-select mr10"></el-cascader>
                </el-form-item>

                <el-form-item label="详细地址：" prop="grade">
                    <el-input v-model="form.detailedAddress" placeholder="请输入详细地址"></el-input>
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
        <el-dialog title="添加用户" v-model="addVisible" width="30%">
            <el-form :model="ruleForm" ref="ruleFormRef" :rules="addRules" label-width="100px">
            <!-- <el-form rules="rules" label-width="70px"> -->
                <el-form-item label="公司ID：" prop="companyId">
                    <el-input v-model="ruleForm.companyId" placeholder="请输入公司ID"></el-input>
                </el-form-item>
                <el-form-item label="公司名：" prop="companyName">
                    <el-input v-model="ruleForm.companyName" placeholder="请输入公司名"></el-input>
                </el-form-item>
                <el-form-item label="联系方式：" prop="contactDetails">
                    <el-input v-model="ruleForm.contactDetails" placeholder="请输入联系方式（邮箱或电话）"></el-input>
                </el-form-item>
                <el-form-item label="地址：" prop="professionalId">
                    <el-cascader :options="areaData" clearable v-model="ruleForm.address" placeholder="地址" style="width:350px" class="handle-select mr10"></el-cascader>
                </el-form-item>
                <el-form-item label="详细地址：" prop="grade">
                    <el-input v-model="ruleForm.detailedAddress" placeholder="请输入详细地址"></el-input>
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
import { provinceAndCityData, regionData, CodeToText, TextToCode } from 'element-china-area-data'


export default {
    name: "GraduateTable",
    setup() {
        // 可视化 相关数据
        const areaData = regionData;
        console.log(areaData);
        const addVisible = ref(false);
        const editVisible = ref(false);
        // data 相关数据
        const companyData = ref([]);

        const pageTotal = ref(0);
        const menu = ref([]);
        // request 相关数据
        const path = "/company/queryCompany";
        const query = reactive({
            mode:"uid",
            options:"all",          
            pageIndex:1,
            pageSize:10,

        });
        // 表单
        const form = reactive({
            companyId:"",
            companyName: "",
            contactDetails: "",
            address: "",
            detailedAddress:"",
        });
        // 规则校验表单
        const ruleForm = reactive({
            companyId:"",
            companyName: "",
            contactDetails: "",
            address: "",
            detailedAddress:"",
        });
        
        const deleteParam = reactive({
             companyId:"",
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
            graduateId: [
                { required: true, message: '请输入学生ID', trigger: 'blur' },
                { len: 10, message: 'ID长度应为10位！', trigger: 'blur' },
            ],

            graduateName: [
                { required: true, message: '请输入姓名！', trigger: 'blur' },
                { min: 2, max: 20, message: '姓名长度应介于2到20之间', trigger: 'blur' },
                { type: 'string', message: '请输入汉字！', trigger: 'blur' },
            ],

            graduatePassword: [
                { required: true, message: '密码不能为空', trigger: 'blur' },
                { min: 6, max: 15, message: '密码位数应在6~15之间', trigger: 'blur' },
                { validator: validatePass, trigger: 'blur' }
            ],
        })

        /** 定义方法 */ 

        // 获取表格数据
        const getFormData = () => {
            getDataParam(query,path).then((res) => {
                console.log(res)
                companyData.value = res.data
                pageTotal.value = res.pageTotal || 10
            });
        };
        // 添加公司数据
        const addCompanyData = (data) => {
            insertData(data,"/company/createCompany").then((res) => {
                console.log(res.data);
            });
        }
        // 更新公司数据
        const updateCompanyData = (data) => {
            updateData(data,"/company/updateCompany").then((res) => {
                console.log(res.data)
                // refresh;
            });
        }
        // 删除公司数据
        const deleteCompanyData = (data) => {
            deleteData(data,"/company/deleteCompany").then((res) => {
                console.log(res.data)
                // refresh;
            });
        }

        const getCodeToText = (codeStr, codeArray) => {
            if (null === codeStr && null === codeArray) {
                return null;
            } 
            else if (null === codeArray) {
                codeArray = codeStr.split(",");
            }

            let area = "";
            switch (codeArray.length) {
                case 1:
                    area += CodeToText[codeArray[0]];
                    break;
                case 2:
                    area += CodeToText[codeArray[0]] + "/" + CodeToText[codeArray[1]];
                    break;
                case 3:
                    area +=
                        CodeToText[codeArray[0]] +
                        "/" +
                        CodeToText[codeArray[1]] +
                        "/" +
                        CodeToText[codeArray[2]];
                    break;
                default:
                    break;  
            }
            return area;
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

                deleteParam.companyId = row.companyId;
                deleteCompanyData(deleteParam);
                ElMessage.success("删除成功");
                getFormData();
                // companyData.value.splice(index, 1);
            })
            .catch(() => {});
        };
        let idx = -1;
        // 编辑操作
        const handleEdit = (index, row) => {
            idx = index;            
            Object.keys(form).forEach((item) => {
                form[item] = row[item];
                console.log(form[item]);
            });
            let strings = row.address.split("/")
            console.log(strings);
            form.address=[TextToCode[strings[0]].code,
            TextToCode[strings[0]][strings[1]].code,
            TextToCode[strings[0]][strings[1]][strings[2]].code
            ];
            console.log(form);
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
            form.address = getCodeToText(null, form.address)
            console.log(companyData.value[idx])
            updateCompanyData(form);
            ElMessage.success(`修改第 ${idx + 1} 行成功`);
            getFormData();
        };
        // 保存新增内容 
        const saveCreate = () => {
            addVisible.value = false;
            console.log(ruleForm)
            console.log(ruleForm.address)
            ruleForm.address = getCodeToText(null, ruleForm.address)
            addCompanyData(ruleForm);
            ElMessage.success(`添加新用户成功`);
            getFormData();
        }

        // setup时执行的函数
        getFormData();

        return {
            areaData,
            query,
            companyData,
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
