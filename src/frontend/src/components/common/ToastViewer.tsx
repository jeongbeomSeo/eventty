import React from "react";
import '@toast-ui/editor/dist/toastui-editor.css';
import {Viewer} from "@toast-ui/react-editor";

interface IToastViewer {
    content?: string;
    viewerRef: React.RefObject<Viewer>;
}

function ToastViewer({content, viewerRef}: IToastViewer) {
    return (
        <>
            <Viewer
                initialValue={content}
                customHTMLRenderer={{
                    htmlBlock: {
                        iframe(node: any) {
                            return [
                                {
                                    type: "openTag",
                                    tagName: "iframe",
                                    outerNewLine: true,
                                    attributes: node.attributes,
                                },
                                {
                                    type: "html",
                                    content: node.childrenHTML
                                },
                                {
                                    type: "closeTag",
                                    tagName: "iframe",
                                    outerNewLine: false,
                                },
                            ]
                        }
                    }
                }}
            />
        </>
    );
}

export default ToastViewer;