import React, {useEffect} from "react";
import {Editor} from "@toast-ui/react-editor";
import '@toast-ui/editor/dist/toastui-editor.css';

interface IToastEditor {
    content?: string;
    editorRef: React.RefObject<Editor>;
    onChange: () => void;
}

function ToastEditor({content, editorRef, onChange}: IToastEditor) {

    const handleSave = () => {
        const markDownContent = editorRef.current?.getInstance().getMarkdown();
        // const htmlContent = editorRef.current?.getInstance().getHTML();

        console.log(markDownContent);
    }

    return (
        <div>
            <Editor
                ref={editorRef}
                initialValue={content ? content : " "}
                height={"70vh"}
                previewStyle={"vertical"}
                previewHighlight={false}
                initialEditType={"wysiwyg"}
                autofocus={false}
                language={"ko"}
                onChange={onChange}
                // 통계 수집 비활성
                usageStatistics={false}
            />
        </div>
    );
}

export default ToastEditor;