<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item>
                    <i class="el-icon-pie-chart"></i> schart图表
                </el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            <div class="plugins-tips">
                vue-schart：vue.js封装sChart.js的图表组件。
                访问地址：
                <a href="https://github.com/lin-xin/vue-schart" target="_blank">vue-schart</a>
            </div>
            <div class="schart-box">
                <div class="content-title">柱状图</div>
                <schart class="schart" canvasId="bar" :options="options1"></schart>
            </div>
        </div>
    </div>
</template>

<script>
import Schart from "vue-schart";
export default {
    name: "basecharts",
    components: {
        Schart,
    },
    setup() {
        const options1 = {
            type: "bar",
            title: {
                text: "就业去向top5",
            },
            bgColor: "#fbfbfb",
            labels: ["阿里巴巴", "华为", "研究所", "腾讯", "字节跳动"],
            datasets: [
                {
                    label: "计算机科学与技术",
                    fillColor: "rgba(241, 49, 74, 0.5)",
                    data: [234, 278, 270, 190, 230],
                },
                {
                    label: "物联网",
                    data: [164, 178, 190, 135, 160],
                },
                {
                    label: "人工智能",
                    data: [144, 198, 150, 235, 120],
                },
            ],
        };
        return {
            options1,
        };
    },
};
</script>

<style scoped>
.schart-box {
    display: inline-block;
    margin: 20px;
}
.schart {
    width: 600px;
    height: 400px;
}
.content-title {
    clear: both;
    font-weight: 400;
    line-height: 50px;
    margin: 10px 0;
    font-size: 22px;
    color: #1f2f3d;
}
</style>