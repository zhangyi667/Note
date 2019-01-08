package com.joeyzhang.Util

import groovy.io.FileType

class WentingDataAnalyse {
    // private static final String FILE_DIR = "C:\\Users\\wenbo\\OneDrive\\PhD Project\\0Meetings\\2018 07 - 09 worries and random experiment\\ViVitro Nov\\MV6 ViVitro\\csv\\";
    private static final String BASE_DIR = "/Users/zhangyi667/Documents/wenting_data_analyse/"
    private static final String DESTINATION_FILE_DIR = BASE_DIR + "result/"
    private static final int START_LINE = 0;

    void wentingAnalyse() {
        def dir = new File(BASE_DIR + "data/")
        def res = ""
        def count = 0
        def errMsg = ""
        dir.eachFileRecurse (FileType.FILES) { file ->
            try {
                res += parseFiles(file);
                count++
            } catch (Exception e) {
                errMsg = "Error when trying to analyse file ${file.name}.\r"
            }
        }
        writeToResult(res, count, errMsg)
    }

    def writeToResult(String res, int count, String errorMsg) {
        def date = new Date().toString()
        def file = new File(DESTINATION_FILE_DIR + "result-${date}.csv")
        file.createNewFile()
        file.append(res)
    }


    String parseFiles(def file) {
        if (!file.path.endsWith("csv")) {
            return "";
        }
        int count = -1
        def years = []
        int colSize = 11

        def res = ""
        file.eachLine { line ->
            count++
            if (!line) {
                return
            }
            def cols = line.split(",")
            if (!cols) {// blank row
                return
            }
            if (count < START_LINE) return
            if (count == START_LINE) {
                years = cols.toList().subList(1,11)
                return
            }
            def city = cols[0]
            for (int i = 1; i < colSize; i++) {
                res += "${city},${years[i - 1]},${cols[i]}" + "\r\n"
            }
        }

//        print res

        return res
    }

    public static void main(String[] args) {
        new WentingDataAnalyse().wentingAnalyse();
    }
}
