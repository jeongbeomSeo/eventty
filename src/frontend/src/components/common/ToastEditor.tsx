import React, {useEffect} from "react";
import {Editor} from "@toast-ui/react-editor";
import '@toast-ui/editor/dist/toastui-editor.css';

interface IToastEditor {
    content?: string;
    editorRef: React.RefObject<Editor>;
}

function ToastEditor({content, editorRef}: IToastEditor) {

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
                // 통계 수집 비활성
                usageStatistics={false}
            />
        </div>
    );
}

export default ToastEditor;