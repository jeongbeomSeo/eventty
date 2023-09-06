import React, {useEffect} from "react";
import {Editor} from "@toast-ui/react-editor";
import '@toast-ui/editor/dist/toastui-editor.css';
import {RefCallBack} from "react-hook-form";

interface IToastEditor {
    value?: string;
    editorRef: React.Ref<Editor>;
    // ref: RefCallBack;
    onChange: () => void;
    onBlur: () => void;
}

function ToastEditor({value, editorRef, onChange, onBlur}: IToastEditor) {
    return (
        <div>
            <Editor
                ref={editorRef}
                initialValue={value ? value : " "}
                height={"70vh"}
                previewStyle={"vertical"}
                previewHighlight={false}
                initialEditType={"wysiwyg"}
                language={"ko"}
                onChange={onChange}
                onBlur={onBlur}
                // 통계 수집 비활성
                usageStatistics={false}
            />
        </div>
    );
}

export default ToastEditor;